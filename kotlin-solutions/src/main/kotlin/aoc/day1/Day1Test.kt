package aoc.day1

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

val testCase0 = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""

fun main() {
    println("Tests:")
    aocRunTest(Day1::partA, aocTest(testCase0, 24000, 0))
    aocRunTest(Day1::partB, aocTest(testCase0, 45000, 1))
    println("")
    aocRun(Day1)
}
