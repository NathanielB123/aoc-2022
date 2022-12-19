package aoc.day8

import aoc.utilities.*

object Day8 : AoCSol<Int, Int> {
    override val day: Int
        get() = 8

    override fun partA(input: String): Int {
        val grid = parse(input)
        var count = 0
        val visible = grid.map { it.map { false }.toMutableList() }.toMutableList()

        val indices = grid.indices.map { y -> grid.first().indices.map { it to y } }

        for (row in listOf(indices, indices.transposed()).flatMap {
            listOf(
                it,
                it.map(List<Pair<Int, Int>>::reversed)
            ).flatten()
        }) {
            var prev = -1
            for ((x, y) in row) {
                if (!visible[y][x] && grid[y][x] > prev) {
                    count++
                    visible[y][x] = true
                }
                prev = grid[y][x].coerceAtLeast(prev)
            }
        }
        return count
    }

    override fun partB(input: String): Int {
        val grid = parse(input)
        var max = 0
        for (p in grid.first().indices.flatMap { x -> grid.indices.map { x to it } }) {
            val (x, y) = p
            val scores = mutableListOf<Int>()
            for (dir in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
                var dist = 0
                for (offset in ORDINALS.map(dir::mul)) {
                    val (newX, newY) = p.add(offset)
                    if (newY !in grid.indices || newX !in grid.first().indices) {
                        break
                    }
                    dist++
                    if (grid[newY][newX] >= grid[y][x]) {
                        break
                    }
                }
                scores.add(dist)
            }
            max = max.coerceAtLeast(scores.reduce { a, b -> a * b })
        }
        return max
    }
}

private fun parse(input: String) = input.split("\n").map { it.map(Char::digitToInt) }
