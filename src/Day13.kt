import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {

    class MaybeListEntry(var num: Int?, var list: List<MaybeListEntry>?) {
        fun compare(right: MaybeListEntry): Int {
            if (num != null && right.num != null) {
                if (num!! < right.num!!) {
                    return 1
                } else if (num!! == right.num!!) {
                    return 0
                } else {
                    return -1
                }
            } else if (num != null && right.list != null) {
                return MaybeListEntry(null, listOf<MaybeListEntry>(MaybeListEntry(num, null))).compare(right)
            } else if (list != null && right.num != null) {
                return this.compare(MaybeListEntry(null, listOf<MaybeListEntry>(MaybeListEntry(right.num, null))))
            } else {
                for (i in list!!.indices) {
                    if (right.list!!.size < i + 1) {
                        return -1
                    }
                    var current = list!![i].compare(right.list!![i])
                    if (current != 0) {
                        return current
                    }
                }
                if (list!!.size == right.list!!.size) {
                    return 0
                } else {
                    return 1
                }
            }
            return 0
        }
    }
    class ComparatorOne: Comparator<MaybeListEntry>{
        override fun compare(o1: MaybeListEntry, o2: MaybeListEntry): Int {
            return o1.compare(o2)
        }
    }
    fun readNumber(s:String, start:Int): Int {
        return s.substring(start).split(',')[0].toInt()
    }
    fun findEnd(s: String, start:Int): Int {
        var stack = Stack<Char>()
        stack.push(s[start])
        var current = start
        while(!stack.isEmpty()) {
            current+=1
            if (s[current] == '[') {
                stack.push('[')
            } else if (s[current] == ']') {
                stack.pop()
            }
        }
        return current
    }
    fun parse(s: String): List<MaybeListEntry> {
        var list = mutableListOf<MaybeListEntry>()
        var st = s.substring(1, s.length-1)
        var current = 0
        while(current < st.length) {
            if (st[current] == '[') {
                var listEnd = findEnd(st, current)
                list.add(MaybeListEntry(null, parse(st.substring(current, listEnd+1))))
                current = listEnd+1
            } else if (st[current] == ',') {
              current+=1
            } else {
                // number
                var number = readNumber(st, current)
                list.add(MaybeListEntry(number, null))
                current+=1
                if (number >= 10) {
                    current+=1
                }
            }
        }
        return list
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for(i in input.indices) {
            var strings = input[i].split("\n", "\r\n")
            var left = MaybeListEntry(null, parse(strings[0]))
            var right = MaybeListEntry(null, parse(strings[1]))
            var res = left.compare(right)
            if (res == 1) {
                sum+= (i+1)
            }
        }
        return sum
    }



    fun part2(input: List<String>): Int {
        var sum = 0
        var arr = mutableListOf<MaybeListEntry>()
        for(i in input.indices) {
            var strings = input[i].split("\n", "\r\n")
            var left = MaybeListEntry(null, parse(strings[0]))
            var right = MaybeListEntry(null, parse(strings[1]))
            arr.add(left)
            arr.add(right)

        }
        var sep2 = MaybeListEntry(null, parse("[[2]]"))
        arr.add(sep2)
        var sep6 = MaybeListEntry(null, parse("[[6]]"))
        arr.add(sep6)
        var comparator= ComparatorOne().reversed()
        arr.sortWith(comparator)
        var ret = 1
        for(i in arr.indices) {
            if (arr[i] == sep2) {
                ret *= i+1
            }
            if (arr[i] == sep6) {
                ret *= i+1
            }
        }
        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByEmptyLines("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInputSplitByEmptyLines("Day13")
    println(part1(input))
    println(part2(input))
}
