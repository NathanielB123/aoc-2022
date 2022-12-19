package aoc.day19

import aoc.utilities.*

object Day19 : AoCSol<Int, Int> {
    override val day: Int
        get() = 19

    override fun partA(input: String): Int =
        parse(input).mapIndexed { i, b -> (i + 1) * recursiveSolve(b, 24, START_RES).geodes }.sum()

    override fun partB(input: String): Int =
        parse(input).take(3).map { recursiveSolve(it, 32, START_RES).geodes }
            .reduce { a, b -> a * b }
}

private val START_RES = Resources(1, 0, 0, 0, 0, 0, 0, 0)

private fun parse(input: String) = input.split("\n").map {
    val line = it.split(" ")
    Costs(
        line[6].toInt(),
        line[12].toInt(),
        line[18].toInt() to line[21].toInt(),
        line[27].toInt() to line[30].toInt()
    )
}

private fun recursiveSolve(
    cs: Costs,
    time: Int,
    res: Resources,
    mem: MutableMap<MemoiseKey, Resources> = mutableMapOf()
): Resources {
    if (time == 0) return res
    val mk = MemoiseKey(res, cs, time)
    if (!mem.containsKey(mk)) {
        mem[mk] = res.nextResources(cs).maxOf { recursiveSolve(cs, time - 1, it, mem) }
    }
    return mem[mk]!!
}

private class Costs(val ore: Int, val clay: Int, val obs: Pair<Int, Int>, val geode: Pair<Int, Int>)

private class Resources(
    var oreRobots: Int,
    var clayRobots: Int,
    var obsRobots: Int,
    var geodeRobots: Int,
    var ore: Int,
    var clay: Int,
    var obs: Int,
    var geodes: Int
) : Comparable<Resources> {

    fun isValid(): Boolean =
        ore >= 0 && clay >= 0 && obs >= 0 && geodes >= 0

    fun incrResources() {
        ore += oreRobots
        clay += clayRobots
        obs += obsRobots
        geodes += geodeRobots
    }

    fun nextResources(cs: Costs): List<Resources> =
        NEXTS.mapNotNull { f -> copy().takeIf { it.f(cs) } }

    private fun copy(): Resources =
        Resources(oreRobots, clayRobots, obsRobots, geodeRobots, ore, clay, obs, geodes)

    override fun compareTo(other: Resources): Int =
        geodes.compareTo(other.geodes)

    override fun toString(): String =
        "BOTS: $oreRobots, $clayRobots, $obsRobots, $geodeRobots, ORE: $ore, CLY: $clay, OBS: $obs, GE: $geodes"
}

private const val PRECISION = 4

private class MemoiseKey(
    val distToOre: Int?,
    val distToClay: Int?,
    val distToObs: Pair<Int, Int>?,
    val distToGeode: Pair<Int, Int>?,
    val oreRobots: Int,
    val clayRobots: Int,
    val obsRobots: Int,
    val geodeRobots: Int,
    val time: Int,
    val numGeodes: Int
) {
    // Reduce the search space so memoisation is actually tractable
    // Anything over 4* the cost of something probably doesn't matter
    constructor(res: Resources, cs: Costs, time: Int) : this(
        (cs.ore * PRECISION - res.ore).takeIf { it >= 0 },
        (cs.clay * PRECISION - res.ore).takeIf { it >= 0 },
        (cs.obs.mul(PRECISION).sub(res.ore to res.clay)).takeIf { it.first >= 0 && it.second >= 0 },
        (
            cs.geode.mul(PRECISION)
                .sub(res.ore to res.obs)
            ).takeIf { it.first >= 0 && it.second >= 0 },
        res.oreRobots, res.clayRobots, res.obsRobots, res.geodeRobots,
        time,
        res.geodes
    )

    override fun equals(other: Any?): Boolean = delegateEquals(this, other, MEMOISE_KEY_FIELDS)

    override fun hashCode(): Int = delegateHashCode(MEMOISE_KEY_FIELDS)
}

private val MEMOISE_KEY_FIELDS = listOf(
    MemoiseKey::distToOre,
    MemoiseKey::distToClay,
    MemoiseKey::distToObs,
    MemoiseKey::distToGeode,
    MemoiseKey::oreRobots,
    MemoiseKey::clayRobots,
    MemoiseKey::obsRobots,
    MemoiseKey::geodeRobots,
    MemoiseKey::time,
    MemoiseKey::numGeodes
)

private val NEXTS = listOf<Resources.(Costs) -> Boolean>(
    { _ -> incrResources(); true },
    { cs -> ore -= cs.ore; if (!isValid()) return@listOf false; incrResources(); ++oreRobots; true },
    { cs -> ore -= cs.clay; if (!isValid()) return@listOf false; incrResources(); ++clayRobots; true },
    { cs -> ore -= cs.obs.first; clay -= cs.obs.second; if (!isValid()) return@listOf false; incrResources(); ++obsRobots; true },
    { cs -> ore -= cs.geode.first; obs -= cs.geode.second; if (!isValid()) return@listOf false; incrResources(); ++geodeRobots; true }
)
