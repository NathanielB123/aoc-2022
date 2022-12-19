package aoc.day3

import aoc.utilities.*

object Day3 : AoCSol<Int, Int> {
    override val day: Int
        get() = 3

    override fun partA(input: String): Int = input.split("\n").map { it.toList() }.map {
        it.take(it.size / 2).toSet().intersect(it.takeLast(it.size / 2).toSet()).first()
    }.score()

    override fun partB(input: String): Int {
        val backpacks = input.split("\n").map { it.toSet() }
        return backpacks.chunked(3).map {
            it.reduce(Set<Char>::intersect).first()
        }.score()
    }
}

private fun List<Char>.score() =
    sumOf { c -> if (c.isLowerCase()) c.code - 'a'.code + 1 else c.code - 'A'.code + 27 }
