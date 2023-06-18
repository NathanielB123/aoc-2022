package aoc.day5

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""

fun main() {
    println("Tests:")
    aocRunTest(Day5::partA, aocTest(testCase0, "CMZ", 0))
    aocRunTest(Day5::partB, aocTest(testCase0, "MCD", 1))
    println("")
    aocRun(Day5)
}
