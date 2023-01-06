import java.lang.Math
import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>): Int {
        var s = input[0]
        for(i in 3 until s.length) {
            var set = mutableSetOf<Char>()
            var valid = true
            for(j in i-3..i) {
                if (set.contains(s[j])) {
                    valid = false
                } else {
                    set.add(s[j])
                }
            }
            if (valid) {
                return i+1
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {

        var s = input[0]
        for(i in 13 until s.length) {
            var set = mutableSetOf<Char>()
            var valid = true
            for(j in i-13..i) {
                if (set.contains(s[j])) {
                    valid = false
                    break
                } else {
                    set.add(s[j])
                }
            }
            if (valid) {
                return i+1
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
