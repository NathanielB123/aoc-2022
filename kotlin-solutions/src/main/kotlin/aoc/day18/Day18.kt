package aoc.day18

import aoc.utilities.*

object Day18 : AoCSol<Int, Int> {
    override val day: Int
        get() = 18

    override fun partA(input: String): Int {
        val parsed = parse(input)
        return parsed.sumOf { p ->
            UNIT_VEC3S.filter { p.add(it) !in parsed }.size
        }
    }

    override fun partB(input: String): Int {
        val minDimBox = Box<Int?>(null)
        val maxDimBox = Box<Int?>(null)

        val parsed = parse(input, minDimBox, maxDimBox)
            .associateWith { State.INSIDE }.toMutableMap()

        val minDim = minDimBox.t
        val maxDim = maxDimBox.t

        return parsed.keys.toList().sumOf { p ->
            UNIT_VEC3S.filter {
                val toUpdate = mutableSetOf<Triple<Int, Int, Int>>()
                val outside = p.add(it).isOutside(
                    parsed,
                    toUpdate,
                    minDim!!,
                    maxDim!!
                )
                toUpdate.forEach { parsed[it] = if (outside) State.OUTSIDE else State.INSIDE }
                outside
            }.size
        }
    }
}

private fun parse(input: String, minDim: Box<Int?>? = null, maxDim: Box<Int?>? = null) =
    input.split("\n").map {
        it.split(",").map {
            it.toInt().also {
                minDim?.t = minDim?.t?.coerceAtMost(it) ?: it
                maxDim?.t = maxDim?.t?.coerceAtLeast(it) ?: it
            }
        }.toTriple()!!
    }.toSet()

private enum class State {
    INSIDE,
    OUTSIDE
}

private fun Triple<Int, Int, Int>.isOutside(
    s: Map<Triple<Int, Int, Int>, State>,
    fs: MutableSet<Triple<Int, Int, Int>>,
    minDim: Int,
    maxDim: Int
): Boolean {
    if (this.first > maxDim || this.second > maxDim || this.third > maxDim || this.first < minDim || this.second < minDim || this.third < minDim) {
        return true
    }

    if (this in s) {
        return (s[this]!! == State.OUTSIDE)
    }

    fs.add(this)

    return UNIT_VEC3S.map { this.add(it) }.filter { it !in fs }
        .any { it.isOutside(s, fs, minDim, maxDim) }
}
