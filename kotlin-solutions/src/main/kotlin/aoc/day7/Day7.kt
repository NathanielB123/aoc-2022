package aoc.day7

import aoc.utilities.*
import java.util.*

const val TARGET_SPACE = 70000000 - 30000000

object Day7 : AoCSol<Int, Int> {
    override val day: Int
        get() = 7

    override fun partA(input: String): Int =
        directorySizes(input, Optional.empty()).filter { it <= 100000 }.sum()

    override fun partB(input: String): Int {
        val start = Directory(null, mutableMapOf(), 0)
        return directorySizes(
            input,
            Optional.of(start)
        ).filter { it >= start.totalSize - TARGET_SPACE }.min()
    }
}

class Directory(val parent: Directory?, val m: MutableMap<String, Directory>, var s: Int) {
    val totalSize: Int by lazy {
        s + m.entries.filter { it.key != ".." }.map { it.value }.sumOf(Directory::totalSize)
    }
}

fun directorySizes(input: String, start: Optional<Directory>): List<Int> = buildList {
    var cur: Directory = start.orElse(Directory(null, mutableMapOf(), 0))
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
