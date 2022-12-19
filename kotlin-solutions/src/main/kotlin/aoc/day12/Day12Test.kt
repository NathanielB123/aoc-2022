package aoc.day12

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""

fun main() {
    println("Tests:")
    aocRunTest(Day12::partA, aocTest(testCase0, 31, 0))
    aocRunTest(Day12::partB, aocTest(testCase0, 29, 1))
    println("")
    aocRun(Day12)
}
