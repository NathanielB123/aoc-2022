package aoc.day25

import aoc.utilities.*

object Day25 : AoCSol<String, Int> {
    override val day: Int
        get() = 25

    override fun partA(input: String): String {
        /*listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 2022, 12345, 314159265, 4890).map(::toSNAFU)
            .forEach(::println)*/
        return toSNAFU(input.split("\n").map { fromSNAFU(it) }.sum())
    }

    override fun partB(input: String): Int {
        return 0
    }
}

private fun Long.pow(other: Long): Long = (1..other).map { this }.fold(1, Long::times)

private fun fromSNAFU(input: String): Long {
    return input.reversed().mapIndexed { i, c ->
        (5L).pow(i.toLong()) * when (c) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> throw java.lang.Exception("Ah $c!")
        }
    }.sum()
}

private fun toSNAFU(input: Long): String {
    // println(input)
    println(input)
    val numDigits = NATS.map { it.toLong() }.map { it to (5L).pow(it) }.first { it.second > input }.first + 1
    val digits = List(numDigits.toInt()) { 0L }.toMutableList()
    var remainingInput = input
    for (i in digits.indices) {
        val placeVal = (5L).pow(digits.size - i - 1L)
        var digit = remainingInput / placeVal
        while (digit > 2 || digit < -2) {
            if (digit > 0) {
                for (j in i - 1 downTo 0) {
                    val prevPlaceVal = (5L).pow(digits.size - j - 1L)
                    if (digits[j] < 2) {
                        ++digits[j]
                        remainingInput -= prevPlaceVal
                        break
                    }
                    remainingInput += (2 + digits[j]) * prevPlaceVal
                    digits[j] = -2
                }
            } else {
                for (j in i - 1 downTo 0) {
                    val prevPlaceVal = (5L).pow(digits.size - j - 1L)
                    if (digits[j] > -2) {
                        --digits[j]
                        remainingInput += prevPlaceVal
                        break
                    }
                    remainingInput += (-2 + digits[j]) * prevPlaceVal
                    digits[j] = 2
                }
            }
            digit = remainingInput / placeVal
        }
        remainingInput -= digit * placeVal
        digits[i] = digit
    }
    if (digits.first() == 0L) digits.removeFirst()
    return digits.map {
        when (it) {
            2L -> '2'
            1L -> '1'
            0L -> '0'
            -1L -> '-'
            -2L -> '='
            else -> throw java.lang.Exception("Ah $it!")
        }
    }.joinToString("") { it.toString() }
}
