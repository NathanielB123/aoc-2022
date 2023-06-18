package aoc.day20

import aoc.utilities.*

object Day20 : AoCSol<Int, Long> {
    override val day: Int
        get() = 20

    override fun partA(input: String): Int {
        val parsed = parse(input)
        val curList = parsed.toMutableList()
        mix(parsed, curList, 1)
        return answer(curList, parsed)
    }

    override fun partB(input: String): Long {
        val parsed = parse(input)
        val curList = parsed.toMutableList()
        for (mix in 1..10) {
            mix(parsed, curList, 811589153L)
        }
        return answer(curList, parsed) * 811589153L
    }
}

private fun parse(input: String) = input.split("\n").map { WrappedInt(it.toInt()) }

private class WrappedInt(val n: Int)

private val READ_INDEXES = listOf(
    1000,
    2000,
    3000
)

private fun answer(
    curList: MutableList<WrappedInt>,
    original: List<WrappedInt>
): Int {
    val zeroIdx = zeroIndex(curList)
    return READ_INDEXES.sumOf { curList[(zeroIdx + it).goodMod(original.size)].n }
}

private fun zeroIndex(curList: MutableList<WrappedInt>) =
    curList.mapIndexedNotNull { i, w -> if (w.n == 0) i else null }.first()

private fun mix(
    original: List<WrappedInt>,
    curList: MutableList<WrappedInt>,
    key: Long = 1
) {
    for (num in original) {
        val i = curList.indexOf(num)
        /* To match example exactly (items moved between the first and last element are put to the
           back instead of the front). */
        // val i2 = Math.floorMod(i + num.n * key - 1, original.size - 1) + 1
        val i2 = Math.floorMod(i + num.n * key, original.size - 1)
        curList.add(i2, curList.removeAt(i))
    }
}
