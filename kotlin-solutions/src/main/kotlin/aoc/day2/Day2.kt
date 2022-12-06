package aoc.day2

import aoc.utilities.*

// Result values are 0 on loss, 1 on draw and 2 on win

fun toScore(rps: Int, result: Int) = rps + 1 + result * 3
fun result(rpsA: Int, rpsB: Int) = (rpsB - rpsA + 1).goodMod(3)
fun resultInv(rps: Int, result: Int) = (rps + result - 1).goodMod(3)

object Day2 : AoCSol {
    override val day: Int
        get() = 2

    override fun partA(input: String): Int = parse(input).sumOf { (rpsB, rpsA) ->
        toScore(rpsA, result(rpsA, rpsB))
    }

    override fun partB(input: String): Int = parse(input).sumOf { (rpsB, result) ->
        toScore(resultInv(rpsB, result), result)
    }
}

fun parse(input: String) = input.split("\n")
    .map { l -> l.split(" ").map { it[0].code }.let { (x, y) -> x - 'A'.code to y - 'X'.code } }
