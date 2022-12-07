package aoc.day7

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k"""

fun main() {
    println("Tests:")
    aocRunTest(Day7::partA, aocTest(testCase0, 95437, 0))
    aocRunTest(Day7::partB, aocTest(testCase0, 24933642, 0))
    println("")
    aocRun(Day7)
}
