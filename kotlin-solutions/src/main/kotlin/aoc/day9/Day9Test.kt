package aoc.day9

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"""

const val testCase1 = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"""

fun main() {
    println("Tests:")
    aocRunTest(Day9::partA, aocTest(testCase0, 13, 0))
    aocRunTest(Day9::partB, aocTest(testCase0, 1, 1))
    aocRunTest(Day9::partB, aocTest(testCase1, 36, 2))
    println("")
    aocRun(Day9)
}
