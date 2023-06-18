package aoc.day24

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#"""

fun main() {
    println("Tests:")
    aocRunTest(Day24::partA, aocTest(testCase0, null, 0))
    // aocRunTest(Day24::partB, aocTest(testCase0, null, 0))
    println("")
    aocRun(Day24)
}
