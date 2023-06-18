package aoc.day21

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32"""

fun main() {
    println("Tests:")
    aocRunTest(Day21::partA, aocTest(testCase0, 152, 0))
    aocRunTest(Day21::partB, aocTest(testCase0, 301, 1))
    println("")
    aocRun(Day21)
}
