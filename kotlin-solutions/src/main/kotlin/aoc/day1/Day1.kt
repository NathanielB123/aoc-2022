package aoc.day1

import aoc.utilities.AoCSol
import aoc.utilities.max

object Day1 : AoCSol {
    override val day: Int
        get() = 1

    override fun partA(input: String): Int = parse(input).max()

    override fun partB(input: String): Int = parse(input).sorted().takeLast(3).sum()
}

fun parse(input: String) = input.split("\n\n").map {
    it.split("\n").sumOf(String::toInt)
}
