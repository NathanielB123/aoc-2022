package aoc.day16

import aoc.utilities.*

object Day16 : AoCSol<Int, Int> {
    override val day: Int
        get() = 16

    override fun partA(input: String): Int {
        val parsed = parse(input)
        return recursiveSearch(listOf(initState(30, parsed)), parsed, parsed.values.toSet(), 0)
    }

    override fun partB(input: String): Int {
        println("Warning: My Part B takes a LONG time to complete. It's basically just brute force (no memoisation at all) lol.")
        val parsed = parse(input)
        return recursiveSearch(
            List(2) { initState(26, parsed) },
            parsed,
            parsed.values.toSet(),
            0
        )
    }
}

private fun parse(input: String) = input.split("\n").associate {
    val line = it.split(" ")
    line[1] to Valve(
        line[4].dropLast(1).takeLastWhile { it != '=' }.toInt(),
        line.drop(9).map { it.removeSuffix(",") }
    )
}

private const val START_VALVE = "AA"

private fun initState(time: Int, valves: Map<String, Valve>): State =
    State(time, valves[START_VALVE]!!)

private class Valve(val flow: Int, private val connections: List<String>) {
    fun getConnections(valves: Map<String, Valve>) = connections.map(valves::get).map { it!! }
}

private fun shortestPath(src: Valve, dst: Valve, valves: Map<String, Valve>): Int {
    if (src === dst) return 0
    val toVisit = ArrayDeque<Valve>()
    val dists = mutableMapOf<Valve, Int>()
    toVisit.addLast(src)
    dists[src] = 0
    while (toVisit.isNotEmpty()) {
        val next = toVisit.removeFirst()
        for (adj in next.getConnections(valves)) {
            if (dists[adj] != null) {
                continue
            }
            if (adj === dst) {
                return dists[next]!! + 1
            }
            dists[adj] = dists[next]!! + 1
            toVisit.addLast(adj)
        }
    }
    throw Exception("No path!")
}

private class State(val remaining: Int, val cur: Valve) : Comparable<State> {
    override fun compareTo(other: State): Int = remaining.compareTo(other.remaining)

    operator fun component1() = remaining

    operator fun component2() = cur
}

private fun recursiveSearch(
    states: List<State>,
    valves: Map<String, Valve>,
    unopened: Set<Valve>,
    knownBest: Int
): Int {
    val sorted = states.sortedDescending()
    val (remaining, cur) = sorted.first()
    val rest = sorted.drop(1)

    var best = 0
    for (each in unopened) {
        if (each.flow == 0) continue

        val time = shortestPath(cur, each, valves) + 1
        if (time > remaining) continue
        val newUnopened = unopened - each
        val newRemaining = remaining - time
        val value = newRemaining * each.flow

        val absoluteMax = value + newUnopened.map(Valve::flow).sortedDescending()
            .mapIndexed { i, f ->
                (
                    newRemaining.coerceAtLeast(
                        rest.maxOfOrNull(State::remaining) ?: Int.MIN_VALUE
                    ) - (i + 2)
                    ).coerceAtLeast(0) * f
            }.sum()
        if (absoluteMax <= best || absoluteMax <= knownBest) {
            continue
        }

        best = best.coerceAtLeast(
            value + recursiveSearch(
                rest + listOf(State(newRemaining, each)),
                valves,
                newUnopened,
                best - value
            )
        )
    }
    return best
}
