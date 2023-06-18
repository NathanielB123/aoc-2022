package aoc.day22

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5

        1111
        1111
        1111
        1111
222233334444
222233334444
222233334444
222233334444
        55556666
        55556666
        55556666
        55556666

4

1 6 4 3 2
2 3 5 6 1
3 4 5 2 1
4 6 5 3 1
5 6 2 3 4
6 1 2 5 4"""

fun main() {
    println("Tests:")
    aocRunTest(Day22::partA, aocTest(testCase0, 6032, 0))
    aocRunTest(Day22::partB, aocTest(testCase0, 5031, 1))
    println("")
    aocRun(Day22)
}
