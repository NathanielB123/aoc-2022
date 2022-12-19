package aoc.day14

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""

fun main() {
    println("Tests:")
    aocRunTest(Day14::partA, aocTest(testCase0, 24, 0))
    aocRunTest(Day14::partB, aocTest(testCase0, 93, 1))
    println("")
    aocRun(Day14)
}
