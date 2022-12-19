package aoc.day11

import aoc.utilities.*
import java.math.BigInteger

object Day11 : AoCSol<Int, BigInteger> {
    override val day: Int
        get() = 11

    override fun partA(input: String): Int = helper(input, false, 20).toInt()

    override fun partB(input: String): BigInteger = helper(input, true, 10000)
}

private fun helper(input: String, useFactor: Boolean, rounds: Int): BigInteger {
    val monkeys = input.split("\n\n").map {
        val line = it.split("\n")
        val opLine = line[2].dropWhile { it != '=' }.split(" ").drop(1)
        Monkey(
            line[1].split(", ", " ").drop(4).map { it.toBigInteger() }.toMutableList(),
            { old ->
                when (opLine[1]) {
                    "+" -> { a: BigInteger, b: BigInteger -> a + b }
                    "*" -> { a: BigInteger, b: BigInteger -> a * b }
                    else -> throw Exception("Unexpected operator!")
                }.invoke(
                    old.takeIf { opLine[0] == "old" } ?: opLine[0].toBigInteger(),
                    old.takeIf { opLine[2] == "old" } ?: opLine[2].toBigInteger()
                )
            },
            line[3].split(" ").last().toInt(),
            line[4].split(" ").last().toInt(),
            line[5].split(" ").last().toInt()
        )
    }
    val inspections: MutableList<Int> = List(monkeys.size) { 0 }.toMutableList()
    val factor =
        if (useFactor) monkeys.map { it.testFactor.toBigInteger() }
            .reduce { acc, i -> acc * i } else null
    for (round in 0 until rounds) {
        monkeys.forEachIndexed { i, m ->
            inspections[i] += m.updateItemLevels(factor)
            while (m.items.isNotEmpty()) {
                val item = monkeys[i].items.removeFirst()
                monkeys[m.whereThrow(item)].items.add(item)
            }
        }
    }
    return inspections.sorted()
        .let { it.last().toBigInteger() * it.dropLast(1).last().toBigInteger() }
}

private class Monkey(
    var items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testFactor: Int,
    private val trueThrow: Int,
    private val falseThrow: Int
) {
    fun updateItemLevels(factor: BigInteger?): Int {
        items =
            items.map {
                val result = operation(it)
                if (factor == null) result / (3).toBigInteger() else result % factor
            }.toMutableList()
        return items.count()
    }

    fun whereThrow(x: BigInteger): Int =
        if (x % testFactor.toBigInteger() == BigInteger.ZERO) trueThrow else falseThrow
}
