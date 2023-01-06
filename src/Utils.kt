import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputSplitByEmptyLines(name: String) =
    File("src", "$name.txt")
        .readText()
        .trim()
        .split("\n\n", "\r\n\r\n")

fun readInputSplitByEmptyLinesNoTrim(name: String) =
    File("src", "$name.txt")
        .readText()
        .split("\n\n", "\r\n\r\n")

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun readNumbers(input: List<String>): List<Int> {
    return input.map(String::toInt)
}

fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    // Map: sum - x -> x
    val complements = associateBy { sum - it }
    return firstNotNullOfOrNull { number ->
        val complement = complements[number]
        if (complement != null) Pair(number, complement) else null
    }
}

fun List<Int>.findTripleOfSum(sum: Int): Triple<Int, Int, Int>? =
    firstNotNullOfOrNull { x ->
        findPairOfSum(sum - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }
