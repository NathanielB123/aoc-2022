package aoc.day7

import aoc.utilities.*
import java.util.*

object Day7 : AoCSol<Int, Int> {
    override val day: Int
        get() = 7

    override fun partA(input: String): Int =
        directorySizes(input).filter { it <= 100000 }.sum()

    override fun partB(input: String): Int {
        val start = Directory(null, mutableMapOf(), 0)
        return directorySizes(
            input,
            start
        ).filter { it >= start.totalSize - TARGET_SPACE }.min()
    }
}

private val START = Directory(null, mutableMapOf(), 0)

private const val TARGET_SPACE = 70000000 - 30000000

private class Directory(val parent: Directory?, val m: MutableMap<String, Directory>, var s: Int) {
    val totalSize: Int by lazy {
        s + m.entries.filter { it.key != ".." }.map { it.value }.sumOf(Directory::totalSize)
    }
}

private fun directorySizes(input: String, start: Directory? = null): List<Int> = buildList {
    var cur: Directory = start ?: Directory(null, mutableMapOf(), 0)
    for (line in input.split("\n").let { it.takeLast(it.size - 1) }) {
        val tokens = line.split(" ")
        when (tokens[0]) {
            "$" -> if (tokens[1] == "cd") {
                cur = (if (tokens[2] == "..") cur.parent else cur.m[tokens[2]])!!
            }
            "dir" -> {
                val newDir = Directory(cur, mutableMapOf(), 0)
                add(newDir)
                cur.m[tokens[1]] = newDir
            }
            else -> cur.s += tokens[0].toInt()
        }
    }
}.map(Directory::totalSize)
