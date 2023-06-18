package aoc.day22

import aoc.utilities.*

object Day22 : AoCSol<Int, Int> {
    override val day: Int
        get() = 22

    override fun partA(input: String): Int {
        val (map, pswd) = input.split("\n\n").let { (map, p) ->
            val mapRows = map.split("\n")
            val maxRow = mapRows.maxOf(String::length) + 2
            listOf(List(maxRow) { Tile.WRAP }) + map.split("\n").map { r ->
                List(maxRow) {
                    if (it == 0 || it > r.length) return@List Tile.WRAP
                    when (r[it - 1]) {
                        '.' -> Tile.OPEN
                        '#' -> Tile.IMPASSABLE
                        ' ' -> Tile.WRAP
                        else -> throw java.lang.Exception("Ah!")
                    }
                }
            } + listOf(List(maxRow) { Tile.WRAP }) to p.splitInclSeps("L", "R").map {
                when (it) {
                    "L" -> Turn.LEFT
                    "R" -> Turn.RIGHT
                    else -> Fwd(it.toInt())
                }
            }
        }
        var pos = map[1].indexOfFirst { it == Tile.OPEN } to 1
        var dir = 1 to 0
        for (cmd in pswd) {
            when (cmd) {
                is Fwd -> {
                    for (i in 1..cmd.num) {
                        val nextPos = pos.add(dir)
                        when (map[nextPos.second][nextPos.first]) {
                            Tile.OPEN -> pos = nextPos
                            Tile.IMPASSABLE -> break
                            Tile.WRAP -> {
                                val prevPos = pos
                                var wrapPos = prevPos
                                do {
                                    pos = wrapPos
                                    wrapPos = wrapPos.sub(dir)
                                } while (map[wrapPos.second][wrapPos.first] != Tile.WRAP)
                                if (map[pos.second][pos.first] == Tile.IMPASSABLE) {
                                    pos = prevPos
                                    break
                                }
                            }
                        }
                    }
                }
                Turn.LEFT -> {
                    dir = dir.second to -dir.first
                }
                Turn.RIGHT -> {
                    dir = -dir.second to dir.first
                }
            }
        }
        return 1000 * pos.second + 4 * pos.first + dirToTurns(dir)
    }

    override fun partB(input: String): Int {
        val (map, pswd, enumerated, faceSize, adj) = input.split("\n\n").let { segments ->
            val map = segments[0]
            val mapRows = map.split("\n")
            val maxRow = mapRows.maxOf(String::length) + 2
            Quintuple(
                listOf(List(maxRow) { Tile.WRAP }) + map.split("\n").map { r ->
                    List(maxRow) {
                        if (it == 0 || it > r.length) return@List Tile.WRAP
                        when (r[it - 1]) {
                            '.' -> Tile.OPEN
                            '#' -> Tile.IMPASSABLE
                            ' ' -> Tile.WRAP
                            else -> throw java.lang.Exception("Ah!")
                        }
                    }
                } + listOf(List(maxRow) { Tile.WRAP }),
                segments[1].splitInclSeps("L", "R").map {
                    when (it) {
                        "L" -> Turn.LEFT
                        "R" -> Turn.RIGHT
                        else -> Fwd(it.toInt())
                    }
                },
                segments[2].split("\n").map { it.map { it.takeIf { it != ' ' }?.digitToInt() } },
                segments[3].toInt(),
                segments[4].split("\n").map { it.split(" ").map(String::toInt) }.associate {
                    it.first() to listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1).zip(
                        it.drop(1).toList()
                    ).toMap()
                }
            )
        }
        val topRights =
            (1..6).associateWith { i ->
                enumerated.flatMapIndexed { y, r ->
                    r.mapIndexedNotNull { x, i2 -> (x to y).takeIf { i == i2 } }
                }.first().add(1 to 1)
            }
        var pos = map[1].indexOfFirst { it == Tile.OPEN } to 1
        var dir = 1 to 0
        for (cmd in pswd) {
            when (cmd) {
                is Fwd -> {
                    for (i in 1..cmd.num) {
                        val nextPos = pos.add(dir)
                        when (map[nextPos.second][nextPos.first]) {
                            Tile.OPEN -> pos = nextPos
                            Tile.IMPASSABLE -> break
                            Tile.WRAP -> {
                                val prevFace = enumerated[pos.second - 1][pos.first - 1]!!
                                val nextFace = adj[prevFace]!![dir]!!
                                val outgoingDir =
                                    adj[nextFace]!!.entries.first { it.value == prevFace }.key
                                val relPos =
                                    (pos.first - 1) % faceSize to (pos.second - 1) % faceSize
                                val turns =
                                    (dirToTurns(outgoingDir) - dirToTurns(-dir.first to -dir.second)).goodMod(
                                        4
                                    )
                                var newRelPos = relPos.letN(turns) { turn2(it, faceSize) }
                                if (outgoingDir.first == 0) {
                                    newRelPos = newRelPos.first to faceSize - newRelPos.second - 1
                                } else {
                                    newRelPos = faceSize - newRelPos.first - 1 to newRelPos.second
                                }
                                val wrapPos = topRights[nextFace]!!.add(newRelPos)
                                if (map[wrapPos.second][wrapPos.first] == Tile.IMPASSABLE) break
                                pos = wrapPos
                                dir = -outgoingDir.first to -outgoingDir.second
                            }
                        }
                    }
                }
                Turn.LEFT -> {
                    dir = dir.second to -dir.first
                }
                Turn.RIGHT -> {
                    dir = -dir.second to dir.first
                }
            }
        }
        return 1000 * pos.second + 4 * pos.first + dirToTurns(dir)
    }
}

private fun dirToTurns(dir: Pair<Int, Int>) = when (dir) {
    1 to 0 -> 0
    0 to 1 -> 1
    -1 to 0 -> 2
    0 to -1 -> 3
    else -> throw java.lang.Exception("Ah!")
}

private fun turn2(dir: Pair<Int, Int>, fs: Int): Pair<Int, Int> = fs - dir.second - 1 to dir.first

private enum class Tile {
    WRAP, IMPASSABLE, OPEN
}

private sealed interface Move

private class Fwd(val num: Int) : Move {
    override fun toString(): String = "Fwd $num"
}

private enum class Turn : Move {
    LEFT,
    RIGHT
}
