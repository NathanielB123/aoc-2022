package aoc.day10

import aoc.utilities.*

object Day10 : AoCSol<Int, String> {
    override val day: Int
        get() = 10

    override fun partA(input: String): Int {
        var total = 0
        executeInstructions(input) { c, x -> if ((c + 20) % 40 == 0) total += c * x }
        return total
    }

    override fun partB(input: String): String {
        val draw = mutableListOf<Char>()
        executeInstructions(input) { c, x -> draw.add(if (((c - 1) % 40 - x).abs() <= 1) '#' else '.') }
        return "\n" + draw.chunked(40).map { it.joinToString("") { it.toString() } }
            .joinToString("\n") { it }
    }
}

fun executeInstructions(input: String, perCycle: (Int, Int) -> Unit) {
    var x = 1
    var cycle = 0

    for (line in input.split("\n").map { it.split(" ") }) {
        perCycle(++cycle, x)
        if (line[0] != "addx") {
            continue
        }
        perCycle(++cycle, x)
        x += line[1].toInt()
    }
}
