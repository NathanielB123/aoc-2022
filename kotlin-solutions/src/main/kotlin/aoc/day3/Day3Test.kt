package aoc.day3

import aoc.utilities.aocRun
import aoc.utilities.aocRunTest
import aoc.utilities.aocTest

const val testCase0 = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"""

fun main() {
    println("Tests:")
    aocRunTest(Day3::partA, aocTest(testCase0, 157, 0))
    aocRunTest(Day3::partB, aocTest(testCase0, 70, 1))
    println("")
    aocRun(Day3)
}
