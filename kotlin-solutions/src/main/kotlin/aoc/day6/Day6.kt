package aoc.day6

import aoc.utilities.*

object Day6 : AoCSol<Int, Int> {
    override val day: Int
        get() = 6

    override fun partA(input: String): Int = firstMarker(input, 4)

    override fun partB(input: String): Int = firstMarker(input, 14)
}

fun firstMarker(input: String, length: Int) = input.map { setOf(it) }
    .letN(length - 1) { it.zip(it.takeLast(it.size - 1), Set<Char>::union) }
    .indexOfFirst { it.size == length } + length
