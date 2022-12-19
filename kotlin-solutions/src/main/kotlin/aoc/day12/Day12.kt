package aoc.day12

import aoc.utilities.*

object Day12 : AoCSol<Int, Int> {
    override val day: Int
        get() = 12

    override fun partA(input: String): Int = helper(input, setOf('S'))

    override fun partB(input: String): Int = helper(input, setOf('S', 'a'))
}

private fun helper(input: String, startChars: Set<Char>): Int {
    val grid = input.split("\n")
    val found = grid.map { it.map { false }.toMutableList() }
    var positions = buildSet {
        grid.forEachIndexed { y, r -> r.forEachIndexed { x, c -> if (c in startChars) add(x to y) } }
    }
    var dist = 0
    while (positions.isNotEmpty()) {
        val newPositions = mutableSetOf<Pair<Int, Int>>()
        for (pos in positions) {
            val (oldX, oldY) = pos
            for (off in listOf(0 to 1, 0 to -1, -1 to 0, 1 to 0)) {
                val (x, y) = pos.add(off)
                if (x >= 0 && y >= 0 && x < grid.first().length && y < grid.size && !found[y][x] && (
                    toHeight(
                            grid[y][x]
                        ) - toHeight(grid[oldY][oldX])
                    ) <= 1
                ) {
                    newPositions.add(x to y)
                    found[y][x] = true
                    if (grid[y][x] == 'E') {
                        return dist + 1
                    }
                }
            }
        }
        dist++
        positions = newPositions
    }
    println(found.toStringCustom())
    throw RuntimeException("No Route!")
}

private fun toHeight(x: Char) = when (x) {
    'S' -> 0
    'E' -> 25
    else -> x.code - 'a'.code
}
