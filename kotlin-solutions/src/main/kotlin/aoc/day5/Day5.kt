package aoc.day5

import aoc.utilities.*

object Day5 : AoCSol {
    override val day: Int
        get() = 5

    override fun partA(input: String): Int {
        val x = input.split("\n\n")
        val crates = x[0].split("\n").map { it.chunked(4).map { it[1] } }.inlinePrint()
        val moves =
            x[1].split("\n").map { it.split(" ").let { Triple(it[1].toInt(), it[3][0], it[5][0]) } }.inlinePrint()
        val stacks =
            MutableList(crates[0].size) { i -> crates.mapNotNull { it[i].takeIf { it != ' ' } } }.groupBy { it.last() }.mapValues { it.value[0].take(it.value[0].size - 1).toMutableList() }
                .inlinePrint()
        for (move in moves) {
            println(move)
            for (i in 0 until move.first) {
                stacks[move.third]!!.add(0, stacks[move.second]!!.removeFirst())
            }
            println(stacks)
        }
        println(stacks.map { it.value.first() }.toString())
        return 0
    }

    override fun partB(input: String): Int {
        val x = input.split("\n\n")
        val crates = x[0].split("\n").map { it.chunked(4).map { it[1] } }
        val moves =
            x[1].split("\n").map { it.split(" ").let { Triple(it[1].toInt(), it[3][0], it[5][0]) } }
        val stacks =
            MutableList(crates[0].size) { i -> crates.mapNotNull { it[i].takeIf { it != ' ' } } }.groupBy { it.last() }.mapValues { it.value[0].take(it.value[0].size - 1).toMutableList() }.toMutableMap()
        println("Part B!")
        for (move in moves) {
            stacks[move.third]!!.addAll(0, stacks[move.second]!!.take(move.first))
            stacks.put(move.second, stacks[move.second]!!.takeLast(stacks[move.second]!!.size - move.first).toMutableList())
        }
        println(stacks.map { it.value.first() }.toString())
        return 0
    }
}
