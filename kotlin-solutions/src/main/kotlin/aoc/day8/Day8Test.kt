package aoc.day8

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """30373
25512
65332
33549
35390"""

fun main() {
    println("Tests:")
    aocRunTest(Day8::partA, aocTest(testCase0, 21, 0))
    aocRunTest(Day8::partB, aocTest(testCase0, null, 0))
    println("")
    aocRun(Day8)
}
