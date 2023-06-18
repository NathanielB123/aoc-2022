package aoc.day23

import aoc.utilities.*

object Day23 : AoCSol<Int, Int> {
    override val day: Int
        get() = 23

    override fun partA(input: String): Int {
        var elves = buildSet {
            input.split("\n")
                .forEachIndexed { y, r -> r.forEachIndexed { x, c -> if (c == '#') add(x to y) } }
        }
        val directions = mutableListOf(0 to -1, 0 to 1, -1 to 0, 1 to 0)
        for (i in 1..10) {
            val proposed = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
            elves.forEach { pos ->
                if (!listOf(
                        1 to 0,
                        1 to 1,
                        0 to 1,
                        -1 to 1,
                        -1 to 0,
                        -1 to -1,
                        0 to -1,
                        1 to -1
                    ).any { pos.add(it) in elves }
                ) return@forEach

                proposed[pos] = pos.add(
                    directions.firstOrNull { d ->
                        !(-1..1).any {
                            pos.add(
                                d.add(
                                    d.flipYX().mul(it)
                                )
                            ) in elves
                        }
                    }
                        ?: return@forEach
                )
            }
            val newElves = (elves - proposed.keys).toMutableSet()
            val proposedPosSet = proposed.values.groupBy { it }.filter { it.value.size == 1 }.keys
            for ((prev, newPos) in proposed) {
                newElves.add(if (newPos in proposedPosSet) newPos else prev)
            }
            elves = newElves
            directions.cycle()
        }
        println(
            (elves.minOf { it.second }..elves.maxOf { it.second }).joinToString("\n") { y ->
                (elves.minOf { it.first }..elves.maxOf { it.first }).joinToString(
                    ""
                ) { x -> if (x to y in elves) "#" else "." }
            }
        )
        return (elves.minOf { it.second }..elves.maxOf { it.second }).flatMap { y -> (elves.minOf { it.first }..elves.maxOf { it.first }).filter { x -> x to y !in elves } }.size
    }

    override fun partB(input: String): Int {
        var elves = buildSet {
            input.split("\n")
                .forEachIndexed { y, r -> r.forEachIndexed { x, c -> if (c == '#') add(x to y) } }
        }
        val directions = mutableListOf(0 to -1, 0 to 1, -1 to 0, 1 to 0)
        for (round in ORDINALS) {
            val proposed = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
            elves.forEach { pos ->
                if (!listOf(
                        1 to 0,
                        1 to 1,
                        0 to 1,
                        -1 to 1,
                        -1 to 0,
                        -1 to -1,
                        0 to -1,
                        1 to -1
                    ).any { pos.add(it) in elves }
                ) return@forEach

                proposed[pos] = pos.add(
                    directions.firstOrNull { d ->
                        !(-1..1).any {
                            pos.add(
                                d.add(
                                    d.flipYX().mul(it)
                                )
                            ) in elves
                        }
                    }
                        ?: return@forEach
                )
            }
            if (proposed.isEmpty()) return round
            val newElves = (elves - proposed.keys).toMutableSet()
            val proposedPosSet = proposed.values.groupBy { it }.filter { it.value.size == 1 }.keys
            for ((prev, newPos) in proposed) {
                newElves.add(if (newPos in proposedPosSet) newPos else prev)
            }
            elves = newElves
            directions.cycle()
        }
        throw java.lang.Exception("Ah!")
    }
}

private fun <T> MutableList<T>.cycle() {
    add(removeFirst())
}

private fun Pair<Int, Int>.flipYX() = second to first
