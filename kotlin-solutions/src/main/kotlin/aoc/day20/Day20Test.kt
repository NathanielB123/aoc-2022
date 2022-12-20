package aoc.day20

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """1
2
-3
3
-2
0
4"""

fun main() {
    println("Tests:")
    aocRunTest(Day20::partA, aocTest(testCase0, 3, 0))
    aocRunTest(Day20::partB, aocTest(testCase0, 1623178306, 0))
    println("")
    aocRun(Day20)
}
