package aoc.day5

import aoc.utilities.*

object Day5 : AoCSol<String, String> {
    override val day: Int
        get() = 5

    override fun partA(input: String): String = helper(input) { addTo, removeFrom, amount ->
        for (i in 0 until amount) addTo.add(0, removeFrom.removeFirst())
    }

    override fun partB(input: String): String = helper(input) { addTo, removeFrom, amount ->
        addTo.addAll(0, removeFrom.take(amount))
        for (i in 0 until amount) removeFrom.removeFirst()
    }
}

private fun helper(
    input: String,
    moveFunc: (MutableList<Char>, MutableList<Char>, Int) -> Unit
): String {
    val (stacks, moves) = parse(input)
    for (move in moves) {
        moveFunc(stacks[move.third]!!, stacks[move.second]!!, move.first)
    }
    return stacks.values.joinToString("") { it.first().toString() }
}

private fun parse(input: String): Pair<MutableMap<Char, MutableList<Char>>, List<Triple<Int, Char, Char>>> {
    val (crateLines, moveLines) = input.split("\n\n").map { it.split("\n") }
    val crates = crateLines.map { it.chunked(4).map { it[1] } }
    val moves =
        moveLines.map { it.split(" ").let { Triple(it[1].toInt(), it[3][0], it[5][0]) } }
    val stacks =
        MutableList(crates[0].size) { i -> crates.mapNotNull { it[i].takeIf { it != ' ' } } }.groupBy { it.last() }
            .mapValues { it.value[0].take(it.value[0].size - 1).toMutableList() }.toMutableMap()
    return stacks to moves
}
