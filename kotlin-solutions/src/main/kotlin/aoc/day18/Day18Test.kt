package aoc.day18

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5"""

fun main() {
    println("Tests:")
    aocRunTest(Day18::partA, aocTest(testCase0, 64, 0))
    aocRunTest(Day18::partB, aocTest(testCase0, 58, 1))
    println("")
    aocRun(Day18)
}
