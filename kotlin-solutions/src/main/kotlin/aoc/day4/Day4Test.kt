package aoc.day4

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""

fun main() {
    println("Tests:")
    aocRunTest(Day4::partA, aocTest(testCase0, 2, 0))
    aocRunTest(Day4::partB, aocTest(testCase0, 4, 1))
    println("")
    aocRun(Day4)
}
