package aoc.day11

import aoc.utilities.*
import java.math.BigInteger

object Day11 : AoCSol<Int, BigInteger> {
    override val day: Int
        get() = 11

    override fun partA(input: String): Int = helper(input, false, 20).toInt()

    override fun partB(input: String): BigInteger = helper(input, true, 10000)
}

fun helper(input: String, useFactor: Boolean, rounds: Int): BigInteger {
    val monkeys = input.split("\n\n").map {
        val monkey = it.split("\n")
        val op = monkey[2].dropWhile { it != '=' }.split(" ").drop(1)
        val parsedOp = { old: BigInteger ->
            when (op[1]) {
                "+" -> { a: BigInteger, b: BigInteger -> a + b }
                "*" -> { a: BigInteger, b: BigInteger -> a * b }
                else -> throw Exception("Unexpected operator!")
            }.invoke(
                old.takeIf { op[0] == "old" } ?: op[0].toBigInteger(),
                old.takeIf { op[2] == "old" } ?: op[2].toBigInteger()
            )
        }
        Monkey(
            monkey[1].split(", ", " ").drop(4).map { it.toBigInteger() }.toMutableList(),
            parsedOp,
            monkey[3].split(" ").last().toInt(),
            monkey[4].split(" ").last().toInt(),
            monkey[5].split(" ").last().toInt()
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

class Monkey(
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
