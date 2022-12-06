package aoc.utilities

typealias AOCTest = ((String) -> Int) -> String?

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path)!!.readText().replace("\r", "")

fun aocTest(input: String?, expected: Int?, testNum: Int): AOCTest =
    fun(aocSol: (String) -> Int): String? =
        input?.takeIf(String::isNotEmpty)?.run(aocSol)?.let { result ->
            "Test $testNum: " + if (expected == null) "Got $result" else {
                if (result == expected) "Success!" else "Fail! Expected: $expected but got: $result"
            }
        }

fun aocRunTest(aocSol: (String) -> Int, test: AOCTest) {
    test(aocSol)?.let(::println)
}

fun aocRun(aocSol: AoCSol) {
    println("Solutions:")
    val input = getResourceAsText(aocSol.inputFilename)
    println("Part A: " + aocSol.partA(input))
    println("Part B: " + aocSol.partB(input))
}
