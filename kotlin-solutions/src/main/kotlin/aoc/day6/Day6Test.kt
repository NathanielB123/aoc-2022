package aoc.day6

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """mjqjpqmgbljsphdztnvjfqwrcgsmlb"""

fun main() {
    println("Tests:")
    aocRunTest(Day6::partA, aocTest(testCase0, 7, 0))
    println("")
    aocRun(Day6)
}
