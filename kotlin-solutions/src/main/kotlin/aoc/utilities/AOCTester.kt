package aoc.utilities

typealias AOCTest<T> = ((String) -> T) -> String?

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path)!!.readText().replace("\r", "")

fun <T> aocTest(input: String?, expected: T?, testNum: Int): AOCTest<T> =
    fun(aocSol: (String) -> T): String? =
        input?.takeIf(String::isNotEmpty)?.run(aocSol)?.let { result ->
            "Test $testNum: " + if (expected == null) "Got $result" else {
                if (result == expected) "Success!" else "Fail! Expected: $expected but got: $result"
            }
        }

fun <T> aocRunTest(aocSol: (String) -> T, test: AOCTest<T>) {
    test(aocSol)?.let(::println)
}

fun <A, B> aocRun(aocSol: AoCSol<A, B>) {
    println("Solutions:")
    val input = getResourceAsText(aocSol.inputFilename)
    println("Part A: " + aocSol.partA(input))
    println("Part B: " + aocSol.partB(input))
}
