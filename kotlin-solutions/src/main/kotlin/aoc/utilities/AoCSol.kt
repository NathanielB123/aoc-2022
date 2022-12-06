package aoc.utilities

interface AoCSol {
    val day: Int

    fun partA(input: String): Int

    fun partB(input: String): Int

    val inputFilename: String
        get() = "/Day${day}Input.txt"
}
