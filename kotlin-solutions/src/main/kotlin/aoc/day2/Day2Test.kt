package aoc.day2

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """A Y
B X
C Z"""

fun main() {
    println("Tests:")
    aocRunTest(Day2::partA, aocTest(testCase0, 15, 0))
    aocRunTest(Day2::partB, aocTest(testCase0, 12, 1))
    println("")
    aocRun(Day2)
}
