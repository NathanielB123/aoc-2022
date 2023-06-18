package aoc.day21

import aoc.utilities.*

object Day21 : AoCSol<Long, Long> {
    override val day: Int
        get() = 21

    override fun partA(input: String): Long {
        val (constants, parsedRemaining) = commonParse(input)
        val parsedConsts = constants.associate { it.first to Expr(it.second, 0.0) }.toMutableMap()
        return solve(parsedConsts, parsedRemaining) { first, second, rootOp ->
            rootOp(
                first,
                second
            ).const.toLong()
        }
    }

    override fun partB(input: String): Long {
        val (constants, parsedRemaining) = commonParse(input)
        val parsedConstants =
            constants.associate {
                it.first to if (it.first == "humn") Expr(0.0, 1.0) else Expr(
                    it.second,
                    0.0
                )
            }.toMutableMap()
        return solve(parsedConstants, parsedRemaining) { first, second, _ -> first.solve(second) }
    }
}

private fun solve(
    map: MutableMap<String, Expr>,
    remaining: MutableList<Operation>,
    onRoot: (Expr, Expr, (Expr, Expr) -> Expr) -> Long
): Long {
    while (true) {
        remaining.inlineRemoveAll {
            val first = map[it.dependencies[0]]
            val second = map[it.dependencies[1]]
            if (first == null || second == null) return@inlineRemoveAll false
            if (it.name == "root") return onRoot(first, second, it.operation)
            val result = it.operation(first, second)
            map[it.name] = result
            return@inlineRemoveAll true
        }
    }
}

private fun commonParse(input: String): Pair<List<Pair<String, Double>>, MutableList<Operation>> {
    val (constants, remaining) = input.split("\n").map {
        val row = it.split(" ")
        row.first().dropLast(1) to row.drop(1)
    }.partition { it.second.size == 1 }
    return constants.map {
        it.first to it.second.last().toLong().toDouble()
    } to remaining.map {
        Operation(
            it.first,
            listOf(it.second[0], it.second[2]),
            when (it.second[1]) {
                "+" -> Expr::plus
                "-" -> Expr::minus
                "*" -> Expr::times
                "/" -> Expr::div
                else -> throw java.lang.Exception("Ah!")
            }
        )
    }.toMutableList()
}

private class Operation(
    val name: String,
    val dependencies: List<String>,
    val operation: (Expr, Expr) -> Expr
)

private class Expr(val const: Double, val humCoeff: Double) {
    operator fun plus(other: Expr) = Expr(const + other.const, humCoeff + other.humCoeff)
    operator fun minus(other: Expr) = Expr(const - other.const, humCoeff - other.humCoeff)
    operator fun times(other: Expr): Expr {
        if (humCoeff * other.humCoeff != 0.0) {
            throw Exception("Equations form a quadratic!")
        }
        return Expr(const * other.const, const * other.humCoeff + humCoeff * other.const)
    }

    operator fun div(other: Expr): Expr {
        if (other.humCoeff == 0.0) {
            return Expr(const / other.const, humCoeff / other.const)
        }
        if (other.const == 0.0 && const == 0.0) {
            return Expr(humCoeff / other.humCoeff, 0.0)
        }
        throw Exception("Equations form a non-trivial division!")
    }

    fun solve(other: Expr): Long = ((const - other.const) / (other.humCoeff - humCoeff)).toLong()

    override fun toString(): String = "$const + $humCoeff * x"
}
