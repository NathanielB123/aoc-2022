package aoc.day4

import aoc.utilities.*

object Day4 : AoCSol<Int, Int> {
    override val day: Int
        get() = 4

    override fun partA(input: String): Int =
        parse(input).filter { (x, y) ->
            x.intersect(y).let { it.size == x.count() || it.size == y.count() }
        }.size

    override fun partB(input: String): Int =
        parse(input).filter { (x, y) -> x.intersect(y).isNotEmpty() }.size
}

fun parse(input: String) = input.split("\n").map { l ->
    l.split(",").map {
        it.split("-").map(String::toInt).let(Iterable<Int>::toPair)
            ?.let(Pair<Int, Int>::toIntRange)!!
    }.toPair()!!
}

fun Pair<Int, Int>.toIntRange() = first..second

fun overlaps(a: Pair<Int, Int>, b: Pair<Int, Int>) =
    a.toIntRange().intersect(b.toIntRange()).isNotEmpty()

fun contains(a: IntRange, b: IntRange) =
    a.intersect(b).size in setOf(a.count(), b.count())

fun contains(a: Pair<Int, Int>, b: Pair<Int, Int>) =
    contains(a.toIntRange(), b.toIntRange())
