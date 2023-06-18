package aoc.day23

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """.....
..##.
..#..
.....
..##.
....."""

const val testCase1 = """....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#.."""

fun main() {
    println("Tests:")
    // aocRunTest(Day23::partA, aocTest(testCase0, null, 0))
    // aocRunTest(Day23::partA, aocTest(testCase1, null, 0))
    aocRunTest(Day23::partB, aocTest(testCase1, null, 1))
    println("")
    aocRun(Day23)
}
