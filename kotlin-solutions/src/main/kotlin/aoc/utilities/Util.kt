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

typealias Vec2 = Pair<Int, Int>
typealias Vec3 = Triple<Int, Int, Int>

val UNIT_VEC2S = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)

val UNIT_VEC3S = listOf(
    Triple(1, 0, 0),
    Triple(-1, 0, 0),
    Triple(0, 1, 0),
    Triple(0, -1, 0),
    Triple(0, 0, 1),
    Triple(0, 0, -1)
)

fun Vec2.add(other: Vec2) =
    first + other.first to second + other.second

@JvmName("addLongInt")
fun Pair<Long, Long>.add(other: Pair<Int, Int>): Pair<Long, Long> =
    first + other.first to second + other.second

fun Triple<Int, Int, Int>.add(other: Triple<Int, Int, Int>) =
    Triple(first + other.first, second + other.second, third + other.third)

fun Pair<Int, Int>.mul(x: Int) = first * x to second * x

fun Pair<Int, Int>.sub(other: Pair<Int, Int>) = this.add(other.mul(-1))

fun Pair<Int, Int>.manhattanDist(other: Pair<Int, Int>): Int =
    sub(other).let { it.first.abs() + it.second.abs() }

@JvmName("addLongLong")
fun Pair<Long, Long>.add(other: Pair<Long, Long>) =
    this.first + other.first to this.second + other.second

fun Pair<Long, Long>.mul(x: Long) = first * x to second * x

@JvmName("subLongLong")
fun Pair<Long, Long>.sub(other: Pair<Long, Long>) = this.add(other.mul(-1))

fun Int.sign() = this.compareTo(0)

fun List<List<Boolean>>.toStringCustom() =
    joinToString("\n") { it.joinToString("") { if (it) "T" else "F" } }

fun <T> (() -> Iterator<T>).repeatedIterator(): Iterator<T> = let {
    object : Iterator<T> {
        var iter = it()
        override fun next() = if (iter.hasNext()) iter.next() else {
            iter = it(); iter.next()
        }

        override fun hasNext() = true
    }
}

fun <T> Iterable<T>.repeatedIterator() = (this::iterator).repeatedIterator()

inline fun <reified T, U> delegateEquals(a: T, b: Any?, fields: List<(T) -> U>) =
    a === b || (a as Any?)?.javaClass == b?.javaClass && allEqual(a, b as T, fields)

fun <T, U> allEqual(a: T, b: T, fields: List<(T) -> U>) = fields.all { it(a) == it(b) }

fun <T, U> T.delegateHashCode(fields: List<(T) -> U>) =
    fields.map { it(this).hashCode() }.reduce { hc, i -> 31 * hc + i }

class Box<T>(var t: T)

fun <T> buildFromBox(block: (Box<T?>) -> Any?): T? {
    return Box<T?>(null).also { block(it) }.t
}

operator fun <T> List<T>.get(i: Long) = get(i.toInt())

operator fun <T> MutableList<T>.set(i: Long, x: T) {
    set(i.toInt(), x)
}

// Code is copied from "filterInPlace" but made inline to allow for non-local returns.
inline fun <T> MutableCollection<T>.inlineRemoveAll(predicate: (T) -> Boolean): Boolean {
    var result = false
    with(iterator()) {
        while (hasNext())
            if (predicate(next())) {
                remove()
                result = true
            }
    }
    return result
}

fun String.splitInclSeps(vararg seps: String): List<String> {
    val joinedSeps = seps.joinToString("|") { it }
    return split(Regex("(?<=($joinedSeps))|(?=($joinedSeps))"))
}

class Quadruple<A, B, C, D, E>(val first: A, val second: B, val third: C, val fourth: D) {
    operator fun component1() = first
    operator fun component2() = second
    operator fun component3() = third
    operator fun component4() = fourth
}

class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) {
    operator fun component1() = first
    operator fun component2() = second
    operator fun component3() = third
    operator fun component4() = fourth
    operator fun component5() = fifth
}

operator fun <K1, K2, V> Map<K1, Map<K2, V>>.get(first: K1, second: K2) = get(first)?.get(second)

operator fun <K1, K2, V> Map<K1, Map<K2, V>>.get(key: Pair<K1, K2>) = get(key.first, key.second)

operator fun <K1, K2, V> MutableMap<K1, MutableMap<K2, V>>.set(first: K1, second: K2, item: V) {
    computeIfAbsent(first) { mutableMapOf() }[second] = item
}

operator fun <T> List<List<T>>.get(x: Int, y: Int) = this[y][x]

operator fun <T> List<List<T>>.get(i: Pair<Int, Int>) = get(i.first, i.second)

operator fun <T> List<MutableList<T>>.set(x: Int, y: Int, item: T) {
    this[y][x] = item
}

operator fun <T> List<MutableList<T>>.set(i: Pair<Int, Int>, item: T) {
    set(i.first, i.second, item)
}
