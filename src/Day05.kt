import java.lang.Math
import java.util.Stack

fun main() {
    fun part1(input: List<String>): String {
        var result = ""
        var layout = input[0].split("\n", "\r\n")
        var moves = input[1].split("\n", "\r\n")
        var state = mutableMapOf<Int, Stack<Char>>()

        var num = layout[layout.size-1].split("   ").size
        for(i in 1 .. num) {
            state[i] = Stack<Char>()
        }
        for(i in layout.size-2 downTo 0) {
            var l = layout[i] //.trimEnd().replace("     ", " [*] ").replace("   ", "[*]").split(' ')
            for(index in 0..num) {
                var j = 1 + index * 4
                if (j < l.length) {
                    if (l[j] != ' ') {
                        state[(index + 1)]!!.push(l[j])
                    }
                }
            }
        }

        for(l in moves) {
            var m = l.replace("move ", "").replace("from ", "").replace("to ", "").split(' ')
            for(i in 1..m[0].toInt()) {
                state[m[2].toInt()]!!.push(state[m[1].toInt()]!!.pop())
            }
        }

        for(i in 1 .. num) {
            result+= state[i]!!.pop()
        }

        return result
    }

    fun part2(input: List<String>): String {
        var result = ""
        var layout = input[0].split("\n", "\r\n")
        var moves = input[1].split("\n", "\r\n")
        var state = mutableMapOf<Int, Stack<Char>>()

        var num = layout[layout.size-1].split("   ").size
        for(i in 1 .. num) {
            state[i] = Stack<Char>()
        }
        for(i in layout.size-2 downTo 0) {
            var l = layout[i] //.trimEnd().replace("     ", " [*] ").replace("   ", "[*]").split(' ')
            for(index in 0..num) {
                var j = 1 + index * 4
                if (j < l.length) {
                    if (l[j] != ' ') {
                        state[(index + 1)]!!.push(l[j])
                    }
                }
            }
        }

        for(l in moves) {
            var m = l.replace("move ", "").replace("from ", "").replace("to ", "").split(' ')
            var tmp = Stack<Char>()
            for(i in 1..m[0].toInt()) {
                tmp.push(state[m[1].toInt()]!!.pop())
            }
            for(i in 1..m[0].toInt()) {
                state[m[2].toInt()]!!.push(tmp.pop())
            }
        }

        for(i in 1 .. num) {
            result+= state[i]!!.pop()
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByEmptyLinesNoTrim("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputSplitByEmptyLinesNoTrim("Day05")
    println(part1(input))
    println(part2(input))
}
