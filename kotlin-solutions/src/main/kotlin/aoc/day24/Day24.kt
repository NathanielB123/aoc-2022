package aoc.day24

import aoc.utilities.*

object Day24 : AoCSol<Int, Int> {
    override val day: Int
        get() = 24

    override fun partA(input: String): Int {
        val blizzards = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val map = input.split("\n").mapIndexed { y, r ->
            r.mapIndexed { x, c ->
                when (c) {
                    '#' -> Tile.WALL
                    '.' -> Tile.OPEN
                    '>' -> {
                        blizzards[x to y] = 1 to 0
                        Tile.OPEN
                    }
                    'v' -> {
                        blizzards[x to y] = 0 to 1
                        Tile.OPEN
                    }
                    '<' -> {
                        blizzards[x to y] = -1 to 0
                        Tile.OPEN
                    }
                    '^' -> {
                        blizzards[x to y] = 0 to -1
                        Tile.OPEN
                    }
                    else -> throw java.lang.Exception("Ah!")
                }
            }
        }
        return bfsSolve(1 to 0, map.first().size - 2 to map.size - 1, 0, map, blizzards)
    }

    override fun partB(input: String): Int {
        val blizzards = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val map = input.split("\n").mapIndexed { y, r ->
            r.mapIndexed { x, c ->
                when (c) {
                    '#' -> Tile.WALL
                    '.' -> Tile.OPEN
                    '>' -> {
                        blizzards[x to y] = 1 to 0
                        Tile.OPEN
                    }
                    'v' -> {
                        blizzards[x to y] = 0 to 1
                        Tile.OPEN
                    }
                    '<' -> {
                        blizzards[x to y] = -1 to 0
                        Tile.OPEN
                    }
                    '^' -> {
                        blizzards[x to y] = 0 to -1
                        Tile.OPEN
                    }
                    else -> throw java.lang.Exception("Ah!")
                }
            }
        }
        val stepsA = bfsSolve(1 to 0, map.first().size - 2 to map.size - 1, 0, map, blizzards)
        return stepsA + bfsSolve(map.first().size - 2 to map.size - 1, 1 to 0, stepsA, map, blizzards)
    }
}

fun updateBlizzard(
    start: Pair<Int, Int>,
    dir: Pair<Int, Int>,
    steps: Int,
    width: Int,
    height: Int
): Pair<Int, Int> {
    fun blizzardWrap(x: Int, range: Int) = (x - 1).goodMod(range - 2) + 1
    return start.add(dir.mul(steps))
        .let { blizzardWrap(it.first, width) to blizzardWrap(it.second, height) }
}

fun bfsSolve(
    start: Pair<Int, Int>,
    dest: Pair<Int, Int>,
    startStep: Int = 0,
    map: List<List<Tile>>,
    blizzardStarts: Map<Pair<Int, Int>, Pair<Int, Int>>
): Int {
    var toExpand = mutableSetOf(start)
    var nextToExpand = mutableSetOf<Pair<Int, Int>>()
    var step = startStep
    var blizzards = blizzardStarts.map {
        updateBlizzard(
            it.key,
            it.value,
            step + 1,
            map.first().size,
            map.size
        )
    }.toSet()

    while (true) {
        if (toExpand.isEmpty()) {
            toExpand = nextToExpand
            // println(toExpand)
            nextToExpand = mutableSetOf()
            ++step
            blizzards = blizzardStarts.map {
                updateBlizzard(
                    it.key,
                    it.value,
                    step + 1,
                    map.first().size,
                    map.size
                )
            }.toSet()
        }
        if (toExpand.isEmpty()) throw java.lang.Exception("Ah!")
        val pos = toExpand.first().also(toExpand::remove)
        if (pos == dest) return step
        for (dir in listOf(0 to 1, 1 to 0, 0 to 0, 0 to -1, -1 to 0)) {
            val newPos = pos.add(dir)
            if (newPos.second < 0 || newPos.second >= map.size || map[newPos] != Tile.OPEN || newPos in blizzards) continue
            nextToExpand.add(newPos)
        }
    }
}

enum class Tile {
    OPEN, WALL
}
