package aoc.utilities

interface AoCSol<A, B> {
    val day: Int

    fun partA(input: String): A

    fun partB(input: String): B

    val inputFilename: String
        get() = "/Day${day}Input.txt"
}
