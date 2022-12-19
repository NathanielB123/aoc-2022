package aoc.day13

import aoc.utilities.*

object Day13 : AoCSol<Int, Int> {
    override val day: Int
        get() = 13

    override fun partA(input: String): Int = input.split("\n\n").mapIndexedNotNull { i, p ->
        val (first, second) = p.split("\n")
        (i + 1).takeIf {
            sortIntOrRecList(
                parseToRecList(first),
                parseToRecList(second)
            ) <= 0
        }
    }.sum()

    override fun partB(input: String): Int {
        val div1 = parseToRecList("[[2]]]")
        val div2 = parseToRecList("[[6]]")
        val tmp =
            (
                input.split("\n\n").flatMap { it.split("\n") }
                    .map(::parseToRecList) + listOf(
                    div1,
                    div2
                )
                )
                .sortedWith(::sortIntOrRecList).enumerate()

        return (tmp.find { it.second === div1 }!!.first + 1) *
            (tmp.find { it.second === div2 }!!.first + 1)
    }
}

private fun parseToRecList(line: String): RecursiveList {
    val stack = ArrayDeque<MutableList<IntOrRecList>>()
    val l = mutableListOf<IntOrRecList>()
    stack.addFirst(l)
    val rl = RecursiveList(l)
    val head = { stack.first() }
    for (ch in line.drop(1).dropLast(1).split(',').flatMap {
        it.takeWhile('['::equals).map(Char::toString) + it.dropWhile('['::equals)
            .dropLastWhile(']'::equals) + it.takeLastWhile(']'::equals).map(Char::toString)
    }) {
        when (ch) {
            "[" -> mutableListOf<IntOrRecList>().let {
                head().add(RecursiveList(it))
                stack.addFirst(it)
            }
            "]" -> stack.removeFirst()
            else -> if (ch != "") head().add(PromotedInt(ch.toInt()))
        }
    }
    return rl
}

private sealed interface IntOrRecList

private class PromotedInt(val i: Int) : IntOrRecList

private class RecursiveList(val l: List<IntOrRecList>) : IntOrRecList

private fun sortIntOrRecList(first: IntOrRecList, second: IntOrRecList): Int {
    return when (first) {
        is PromotedInt -> when (second) {
            is PromotedInt -> first.i.compareTo(second.i)
            is RecursiveList -> {
                sortIntOrRecList(
                    RecursiveList(listOf(first)),
                    second
                )
            }
        }
        is RecursiveList -> when (second) {
            is PromotedInt -> {
                sortIntOrRecList(
                    first,
                    RecursiveList(listOf(second))
                )
            }
            is RecursiveList -> when (first.l.isEmpty() to second.l.isEmpty()) {
                (true to true) -> 0
                (true to false) -> -1
                (false to true) -> 1
                (false to false) -> sortIntOrRecList(
                    first.l.first(),
                    second.l.first()
                ).let {
                    if (it == 0) {
                        sortIntOrRecList(
                            RecursiveList(first.l.drop(1)),
                            RecursiveList(second.l.drop(1))
                        )
                    } else it
                }
                else -> {
                    throw Exception("Slightly sad that the compiler can't recognise these cases are exhaustive...")
                }
            }
        }
    }
}
