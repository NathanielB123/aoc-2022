package aoc.day9

import aoc.utilities.*

val dirs = mapOf('U' to (0 to 1), 'D' to (0 to -1), 'L' to (-1 to 0), 'R' to (1 to 0))

fun simulate(input: String, numKnots: Int): Int {
    val positions: MutableSet<Pair<Int, Int>> = mutableSetOf(0 to 0)
    val knots = (0..numKnots).map { 0 to 0 }.toMutableList()
    for (mov in input.split("\n").map { it.split(" ").let { it[0][0] to it[1].toInt() } }) {
        val movVec = dirs[mov.first]!!.mul(mov.second)
        knots[0] = knots[0].add(movVec)
        var needToMove = true
        while (needToMove) {
            needToMove = false
            for (i in 1..numKnots) {
                val following = knots[i - 1]
                val toMove = { knots[i] }
                val diff = { following.sub(toMove()) }
                if (diff().chebyshevLen() > 1) {
                    needToMove = true
                    knots[i] =
                        toMove().add(diff().first.sign() to diff().second.sign())
                    if (i == numKnots) {
                        positions.add(knots[i])
                    }
                }
            }
        }
    }
    return positions.size
}

object Day9 : AoCSol<Int, Int> {
    override val day: Int
        get() = 9

    override fun partA(input: String): Int = simulate(input, 1)

    override fun partB(input: String): Int = simulate(input, 9)
}
