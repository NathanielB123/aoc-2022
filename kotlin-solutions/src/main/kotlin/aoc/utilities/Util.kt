package aoc.utilities

fun Iterable<Int>.max() = maxOf { it }
fun Iterable<Int>.maxOrNull() = maxOfOrNull { it }
fun Iterable<Int>.min() = minOf { it }
fun Iterable<Int>.minOrNull() = minOfOrNull { it }
fun <T> Iterable<T>.enumerate() = (0..count()).zip(this)

fun <T> Iterable<T>.cartesianProduct() = flatMap { f -> map { f to it } }
fun <T> Iterable<T>.indexedCartesianProduct() = (this.enumerate()).cartesianProduct()
fun <T> Iterable<T>.indexedCartesianProductNoDups() =
    indexedCartesianProduct().filter { (f, s) -> f.first != s.first }

fun <T> Iterable<T>.cartesianProductNoDups() =
    indexedCartesianProductNoDups().map { (f, s) -> f.second to s.second }

fun <T> Iterable<T>.toPair() = let { it.first() to it.drop(1).first() }.takeIf { count() > 1 }
fun <T> Iterable<T>.toTriple() =
    let { Triple(it.first(), it.drop(1).first(), it.drop(2).first()) }.takeIf { count() > 2 }

fun Pair<Int, Int>.toIntRangeUntil() = first until second

fun Int.goodMod(other: Int) = Math.floorMod(this, other)
fun Int.square() = this * this
fun Int.abs() = kotlin.math.abs(this)
fun <T> T.inlinePrint(): T = inlinePrint("")
fun <T> T.inlinePrint(msg: String): T = this.also { println(msg + it) }

val NATS = generateSequence(0) { it + 1 }
val ORDINALS = NATS.drop(1)
val SQUARES = NATS.map { it * it }
val EVENS = NATS.filter { it % 2 == 0 }
val ODDS = NATS.filter { it % 2 == 1 }

fun Pair<Int, Int>.add(other: Pair<Int, Int>) =
    this.first + other.first to this.second + other.second

val UNIT_P2S = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)

fun Char.azToInt(): Int? =
    (this.code - 'a'.code).takeIf { ('a'.code..'z'.code).contains(this.code) }

fun Char.uAZToInt(): Int? =
    (this.code - 'A'.code).takeIf { ('A'.code..'Z'.code).contains(this.code) }

fun <T> T.letN(n: Int, op: (T) -> T): T {
    var tmp = this
    for (i in 0 until n) {
        tmp = op(tmp)
    }
    return tmp
}

fun <T> Iterable<Iterable<T>>.transposed(): List<List<T>> =
    List(first().count()) { mutableListOf<T>() }.also { acc ->
        forEach { acc.zip(it, MutableList<T>::add) }
    }

fun <K, V> Map.Entry<K, V>.toPair(): Pair<K, V> = key to value

fun <K, V> Map<K, Iterable<V>>.transposed(): Map<V, List<K>> =
    entries.flatMap { (k, v) -> v.map { k to it } }.groupBy(Pair<K, V>::second, Pair<K, V>::first)

fun <T, U> Iterable<Pair<T, Iterable<U>>>.groupedTranspose(): Iterable<Pair<T, Iterable<U>>> =
    flatMap { (f, s) -> s.map { f to it } }.groupBy(
        Pair<T, U>::first,
        Pair<T, U>::second
    ).entries.map(Map.Entry<T, List<U>>::toPair)

fun Pair<Int, Int>.chebyshevLen() = first.abs().coerceAtLeast(second.abs())

fun Pair<Int, Int>.mul(x: Int) = first * x to second * x

fun Pair<Int, Int>.sub(other: Pair<Int, Int>) = this.add(other.mul(-1))

fun Int.sign() = this.compareTo(0)
