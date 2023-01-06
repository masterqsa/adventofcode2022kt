import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    var addl = 5960464477539062
    fun getLargeConstant(): Long {
        var pow = 1L
        var sum = 0L
        for(i in 0..22) {
            sum+=(2 * pow)
            pow*=5
        }
        return sum
    }
    fun fromSNAFU(s: String): Long {
        var sum = 0L
        var len = s.length
        var pow = 1L
        for(i in len-1 downTo 0) {

            when(s[i]) {
                '2' -> sum+= (2 * pow)
                '1' -> sum+= (1 * pow)
                '0' -> sum+= 0
                '-' -> sum+= (-1 * pow)
                '=' -> sum+= (-2 * pow)
            }
            pow*=5
        }
        return sum
    }
    fun fivetoSNAFU(v: String) : String {
        var res = ""
        for(c in v) {
            when(c) {
                '4' -> res+="2"
                '3' -> res+="1"
                '2' -> res+="0"
                '1' -> res+="-"
                '0' -> res+="="
            }
        }
        return res
    }
    fun toFive(v: Long): String {
        var remainders = Stack<Long>()
        var current = v + addl
        while(current >= 5) {
            remainders.push(current % 5)
            current = current / 5
        }
        var res = ""
        while(!remainders.isEmpty()) {
            res+=remainders.pop()
        }
        return res
    }

    fun part1(input: List<String>): String {
        var sum = 0L
        var c = getLargeConstant()
        for(l in input) {
            sum+=fromSNAFU(l)
        }
        var five = toFive(sum)
        var snafu = fivetoSNAFU(five).trimStart('0')
        return snafu
    }



    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day26_test")
    check(part1(testInput) == "2=-1=0")
    check(part2(testInput) == 0)

    val input = readInput("Day26")
    println(part1(input))
    println(part2(input))
}
