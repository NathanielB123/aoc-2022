package aoc.day17

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"""

fun main() {
    println("Tests:")
    aocRunTest(Day17::partA, aocTest(testCase0, 3068, 0))
    // aocRunTest(Day17::partB, aocTest(testCase0, null, 1))
    println("")
    aocRun(Day17)
}
