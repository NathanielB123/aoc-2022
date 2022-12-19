package aoc.day17

import aoc.utilities.*

object Day17 : AoCSol<Int, Long> {
    override val day: Int
        get() = 17

    override fun partA(input: String): Int {
        val jetsIter = input.map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> throw Exception("Ah!")
            }
        }.repeatedIterator()
        val grid = List(10000) { i -> MutableList(9) { it == 0 || it == 8 || i == 0 } }
        val blocksIter = BLOCKS.repeatedIterator()
        var topHeight = 0L
        for (i in 0 until 2022) {
            val curBlock = blocksIter.next()
            var blockPos = 3L to topHeight + 3L + curBlock.size
            while (true) {
                val currJet = jetsIter.next()
                blockPos = tryApplyOff(
                    grid,
                    curBlock,
                    blockPos,
                    when (currJet) {
                        Direction.LEFT -> -1L
                        Direction.RIGHT -> 1L
                    } to 0L
                ) ?: blockPos
                blockPos = tryApplyOff(grid, curBlock, blockPos, 0L to -1L) ?: break
            }
            for (off in curBlock.indices.flatMap { y -> curBlock.first().indices.map { it to y } }) {
                if (!curBlock[off.second][off.first]) {
                    continue
                }
                val gridPos = blockPos.add(off.first to -off.second)
                if (grid[gridPos.second][gridPos.first]) {
                    throw java.lang.Exception("AH!")
                }
                grid[gridPos.second][gridPos.first] = true
            }
            topHeight = topHeight.coerceAtLeast(blockPos.second)
        }
        return topHeight.toInt()
    }

    override fun partB(input: String): Long {
        val jets = input.map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> throw Exception("Ah!")
            }
        }
        val grid = List(1000000) { i -> MutableList(9) { it == 0 || it == 8 || i == 0 } }
        var topHeight = 0L
        var blockIndex = BLOCKS.size - 1L
        var jetIndex = jets.size - 1L
        val pairs = mutableSetOf<Pair<Long, Long>>()
        var pair: Pair<Long, Long>? = null
        var prev: Pair<Long, Long>? = null
        var topHeightOff: Long? = null
        var i = 0L
        while (i < 1000000000000) {
            ++i
            if (pair == null) {
                if (blockIndex to jetIndex in pairs) {
                    pair = (blockIndex to jetIndex)
                    prev = i to topHeight
                } else {
                    pairs.add(blockIndex to jetIndex)
                }
            } else if (blockIndex to jetIndex == pair) {
                val iDiff = i - prev!!.first
                val hDiff = topHeight - prev.second
                val iters = ((1000000000000 - i) / iDiff).toInt()
                i += iDiff * iters
                topHeightOff = hDiff * iters
            }

            ++blockIndex
            if (blockIndex >= BLOCKS.size) {
                blockIndex = 0
            }
            val curBlock = BLOCKS[blockIndex.toInt()]

            var blockPos = 3L to topHeight + 3L + curBlock.size
            while (true) {
                ++jetIndex
                if (jetIndex >= jets.size) {
                    jetIndex = 0
                }
                val currJet = jets[jetIndex.toInt()]

                blockPos = tryApplyOff(
                    grid,
                    curBlock,
                    blockPos,
                    when (currJet) {
                        Direction.LEFT -> -1L
                        Direction.RIGHT -> 1L
                    } to 0L
                ) ?: blockPos
                blockPos = tryApplyOff(grid, curBlock, blockPos, 0L to -1L) ?: break
            }
            for (off in curBlock.indices.flatMap { y -> curBlock.first().indices.map { it.toLong() to y.toLong() } }) {
                if (!curBlock[off.second.toInt()][off.first.toInt()]) {
                    continue
                }
                val gridPos = blockPos.add(off.first to -off.second)
                if (grid[gridPos.second.toInt()][gridPos.first.toInt()]) {
                    throw java.lang.Exception("AH!")
                }
                grid[gridPos.second.toInt()][gridPos.first.toInt()] = true
            }
            topHeight = topHeight.coerceAtLeast(blockPos.second)
        }
        return topHeight + (topHeightOff ?: 0)
    }
}

private fun tryApplyOff(
    grid: List<List<Boolean>>,
    block: List<List<Boolean>>,
    pos: Pair<Long, Long>,
    offset: Pair<Long, Long>
): Pair<Long, Long>? {
    val newPos = pos.add(offset)
    for (off in block.indices.flatMap { y -> block.first().indices.map { it.toLong() to y.toLong() } }) {
        if (!block[off.second.toInt()][off.first.toInt()]) {
            continue
        }
        val gridPos = newPos.add(off.first to -off.second)
        if (gridPos.first < 0 || gridPos.first >= grid.first().size || gridPos.second < 0 || gridPos.second >= grid.size || grid[gridPos.second.toInt()][gridPos.first.toInt()]) {
            return null
        }
    }
    return newPos
}

private enum class Direction {
    LEFT, RIGHT
}

private val BLOCKS: List<List<List<Boolean>>> = """####

.#.
###
.#.

..#
..#
###

#
#
#
#

##
##""".split("\n\n").map {
    it.split("\n").map {
        it.map {
            when (it) {
                '#' -> true
                '.' -> false
                else -> throw Exception("Ah!")
            }
        }
    }
}
