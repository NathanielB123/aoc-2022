package aoc.day14

import aoc.utilities.*

private const val DROP_HEIGHT = 500

object Day14 : AoCSol<Int, Int> {
    override val day: Int
        get() = 14

    override fun partA(input: String): Int {
        val grid = List(200) { MutableList(1000) { Tile.EMPTY } }
        val maxY = parse(input, grid)
        return simulate(grid, maxY + 1, false)
    }

    override fun partB(input: String): Int {
        val grid = List(200) { MutableList(1000) { Tile.EMPTY } }
        val maxY = parse(input, grid) + 2
        return simulate(grid, maxY, true)
    }
}

private fun parse(
    input: String,
    grid: List<MutableList<Tile>>
): Int {
    val parsed = input.split("\n")
        .map {
            it.split("->").map {
                it.split(",").map { it.removeSuffix(" ").removePrefix(" ").toInt() }
                    .let { it[0] to it[1] }
            }
        }
    var maxY = 0
    for (line in parsed) {
        maxY = maxY.coerceAtLeast((line.map { it.second }).max())
        var prev = line.first()
        for (coord in line.drop(1)) {
            for (x in prev.first.coerceAtMost(coord.first)..prev.first.coerceAtLeast(coord.first)) {
                for (y in prev.second.coerceAtMost(coord.second)..prev.second.coerceAtLeast(
                    coord.second
                )) {
                    grid[y][x] = Tile.ROCK
                }
            }
            prev = coord
        }
    }
    return maxY
}

private fun simulate(
    grid: List<MutableList<Tile>>,
    maxY: Int,
    bottomRow: Boolean
): Int {
    var count = 0
    outer@ while (grid[0][DROP_HEIGHT] == Tile.EMPTY) {
        var sandPos = DROP_HEIGHT to 0
        val indexGrid = { p: Pair<Int, Int> -> grid[p.second][p.first] }
        while (true) {
            if (sandPos.second == maxY - 1) {
                if (bottomRow) break else break@outer
            }
            sandPos = listOf(0 to 1, -1 to 1, 1 to 1).map { sandPos.add(it) }
                .firstOrNull { indexGrid(it) == Tile.EMPTY } ?: break
        }
        grid[sandPos.second][sandPos.first] = Tile.SAND
        count++
    }
    return count
}

private enum class Tile {
    EMPTY, ROCK, SAND
}
