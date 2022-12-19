package aoc.day15

import aoc.utilities.*
import java.math.BigInteger

object Day15 : AoCSol<Int, BigInteger> {
    override val day: Int
        get() = 15

    override fun partA(input: String): Int {
        val parsed = parse(input)
        val beacons = mutableSetOf<Int>()
        val row = PART_A_ROW
        val rowInvalid = mutableListOf<Flag>()
        for ((s, b) in parsed) {
            val md = s.manhattanDist(b)
            val rr = md - (s.second - row).abs()
            if (b.second == row) {
                beacons.add(b.first)
            }
            if (rr < 0) {
                continue
            }
            rowInvalid.add(Flag(-rr + s.first, Marker.START))
            rowInvalid.add(Flag(rr + s.first, Marker.END))
        }
        return rowInvalid.numberCovered() - beacons.size
    }

    override fun partB(input: String): BigInteger {
        val parsed = parse(input)
        val maxRange = MAX_RANGE
        for (possibleRow in (0..maxRange)) {
            val rowInvalid = mutableListOf<Flag>()
            for ((s, b) in parsed) {
                val md = s.manhattanDist(b)
                val rr = md - (s.second - possibleRow).abs()
                if (rr < 0) {
                    continue
                }
                rowInvalid.add(Flag(-rr + s.first, Marker.START))
                rowInvalid.add(Flag(rr + s.first, Marker.END))
            }
            rowInvalid.firstNotContains(0, maxRange)
                ?.let {
                    println(it to possibleRow)
                    return it.toBigInteger() * MAX_RANGE.toBigInteger() + possibleRow.toBigInteger()
                }
        }
        throw Exception("Could not find gap!")
    }
}

private val MAX_RANGE: Int = 4000000

private val PART_A_ROW: Int = 2_000_000

private fun parse(input: String): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val parsed = input.split("\n").map {
        val l = it.split(" ")
        (
            l[2].drop(2).dropLast(1).toInt() to l[3].drop(2).dropLast(1).toInt()
            ) to (
            l[8].drop(2)
                .dropLast(1).toInt() to l[9].drop(2).toInt()
            )
    }
    return parsed
}

private enum class Marker {
    START, END
}

private class Flag(val x: Int, val m: Marker)

private fun List<Flag>.contains(a: Int): Boolean {
    val cpy =
        ArrayDeque(this.sortedWith { f, s -> if (f.x < s.x) -1 else if (f.x == s.x) f.m.compareTo(s.m) else 1 })
    var counter = 0
    while (cpy.isNotEmpty()) {
        val next = cpy.removeFirst()
        if (counter == 0 && next.x > a) {
            assert(next.m == Marker.START)
            return false
        }
        if (next.m == Marker.START) ++counter else --counter
        if (next.x >= a) {
            return true
        }
    }
    return false
}

private fun List<Flag>.firstNotContains(min: Int, max: Int): Int? {
    val cpy =
        ArrayDeque(this.sortedWith { f, s -> if (f.x < s.x) -1 else if (f.x == s.x) f.m.compareTo(s.m) else 1 })
    var counter = 0
    var prev: Int? = null
    while (cpy.isNotEmpty()) {
        val next = cpy.removeFirst()
        if (counter == 0 && prev != null && next.x > prev && prev >= min && prev <= max) {
            assert(next.m == Marker.START)
            return prev
        }
        if (next.m == Marker.START) ++counter else --counter
        prev = next.x + 1
    }
    return null
}

private fun List<Flag>.numberCovered(): Int {
    val cpy =
        ArrayDeque(this.sortedWith { a, b -> if (a.x < b.x) -1 else if (a.x == b.x) a.m.compareTo(b.m) else 1 })
    var prev: Int? = null
    var counter = 0
    var total = 0
    while (cpy.isNotEmpty()) {
        val next = cpy.removeFirst()
        prev = prev ?: next.x
        if (next.m == Marker.START) ++counter else --counter
        if (counter == 0) {
            total += next.x - prev + 1
            prev = null
        }
    }
    return total
}
