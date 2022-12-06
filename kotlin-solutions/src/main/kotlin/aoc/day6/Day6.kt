package aoc.day6

import aoc.utilities.*

object Day6 : AoCSol {
    override val day: Int
        get() = 6

    override fun partA(input: String): Int {
        return input.map { setOf(it) }
            .letN(14) { it.zip(it.takeLast(it.size - 1), Set<Char>::union) }
            .indexOfFirst { it.size == 4 } + 4
    }

    override fun partB(input: String): Int {
        return input.map { setOf(it) }
            .letN(14) { it.zip(it.takeLast(it.size - 1), Set<Char>::union) }
            .indexOfFirst { it.size == 14 } + 14
    }
}
