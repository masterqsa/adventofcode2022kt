import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        // 20th, 60th, 100th, 140th, 180th, and 220th cycles
        var cycleSet = setOf<Int>(20,60,100,140,180,220)
        var total = 0
        var x = 1
        var pendingVal = x
        var applyAt = 0
        var currentLine = 0
        for(c in 1..220) {
            if (currentLine < input.size && applyAt != c) {
                var line = input[currentLine].split(' ')
                if (line[0] == "addx") {
                    pendingVal = x + line[1].toInt()
                    applyAt = c+1
                }
                currentLine+=1
            }
            if (cycleSet.contains(c)) {
                total+=(c * x)
            }
            if (c == applyAt) {
                x = pendingVal
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var x = 1
        var pendingVal = x
        var applyAt = 0
        var currentLine = 0
        for(l in 0..5) {
            var s = ""
            for (p in 1..40) {
                var c = l*40 + p
                if (currentLine < input.size && applyAt != c) {
                    var line = input[currentLine].split(' ')
                    if (line[0] == "addx") {
                        pendingVal = x + line[1].toInt()
                        applyAt = c + 1
                    }
                    currentLine += 1
                }
                if (p <= x+2 &&  p >= x) {
                    s = s+"#"
                } else {
                    s = s+"."
                }
                if (c == applyAt) {
                    x = pendingVal
                }
            }
            println(s)
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
