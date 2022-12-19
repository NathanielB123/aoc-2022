package aoc.day13

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]"""

fun main() {
    println("Tests:")
    aocRunTest(Day13::partA, aocTest(testCase0, 13, 0))
    aocRunTest(Day13::partB, aocTest(testCase0, 140, 1))
    println("")
    aocRun(Day13)
}
