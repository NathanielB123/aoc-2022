package aoc.day25

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122"""

fun main() {
    println("Tests:")
    aocRunTest(Day25::partA, aocTest(testCase0, null, 0))
    // aocRunTest(Day25::partB, aocTest(testCase0, null, 0))
    println("")
    aocRun(Day25)
}

