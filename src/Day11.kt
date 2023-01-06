import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    class Monkey(var items: Queue<Long>, var op: String, var self: Boolean, var param: Int, var divparam: Int, var truedest: Int, var falsedest: Int, var pacifier: Int) {
        var count = 0
        var multdivisor = 1
        var outList = mutableListOf<Pair<Int,Long>>()
        fun operation(x: Long): Long {
            when(op) {
                "*" -> return if (self) {
                    (x*x) % multdivisor
                } else {
                    (x*param) % multdivisor
                }
                "+" -> return if (self) {
                    (x+x) % multdivisor
                } else {
                    (x+param) % multdivisor
                }
            }
            return 0
        }
        fun process() {
            while(!items.isEmpty()) {
                var current = items.poll()
                count+=1
                current = Math.floorDiv(operation(current), pacifier.toLong()).toLong()
                var test = current % divparam == 0L
                if (test) {
                    outList.add(Pair(truedest, current))
                } else {
                    outList.add(Pair(falsedest, current))
                }
            }
        }
    }
    fun part1(input: List<String>): Int {
        var multdivisor = 1
        var monkeys = mutableListOf<Monkey>()
        for(m in input) {
            var split = m.split("\n", "\r\n")
            var items = split[1].trim().replace("Starting items: ", "").split(", ").map { it.trim().toLong() }
            var itemsQ = LinkedList<Long>(items)
            var opsplit = split[2].trim().replace("Operation: new = old ", "").split(' ')
            var op = opsplit[0]
            var self = false
            var param = 0
            if (opsplit[1] == "old") {
                self = true
            } else {
                param = opsplit[1].toInt()
            }
            var divparam = split[3].trim().replace("Test: divisible by ", "").toInt()
            var truedest = split[4].trim().replace("If true: throw to monkey ", "").toInt()
            var falsedest = split[5].trim().replace("If false: throw to monkey ", "").toInt()
            monkeys.add(Monkey(itemsQ, op, self, param, divparam, truedest, falsedest, 3))
            multdivisor *= divparam
        }
        for(m in monkeys) {
            m.multdivisor = multdivisor
        }
        for(i in 1..20) {
            for(m in monkeys.indices) {
                monkeys[m].process()
                for(item in monkeys[m].outList) {
                    monkeys[item.first].items.add(item.second)
                }
                monkeys[m].outList.clear()
            }
        }
        monkeys.sortByDescending { it.count }
        return monkeys[0].count * monkeys[1].count
    }



    fun part2(input: List<String>): Long {
        var multdivisor = 1
        var monkeys = mutableListOf<Monkey>()
        for(m in input) {
            var split = m.split("\n", "\r\n")
            var items = split[1].trim().replace("Starting items: ", "").split(", ").map { it.trim().toLong() }
            var itemsQ = LinkedList<Long>(items)
            var opsplit = split[2].trim().replace("Operation: new = old ", "").split(' ')
            var op = opsplit[0]
            var self = false
            var param = 0
            if (opsplit[1] == "old") {
                self = true
            } else {
                param = opsplit[1].toInt()
            }
            var divparam = split[3].trim().replace("Test: divisible by ", "").toInt()
            var truedest = split[4].trim().replace("If true: throw to monkey ", "").toInt()
            var falsedest = split[5].trim().replace("If false: throw to monkey ", "").toInt()
            monkeys.add(Monkey(itemsQ, op, self, param, divparam, truedest, falsedest, 1))
            multdivisor *= divparam
        }
        for(m in monkeys) {
            m.multdivisor = multdivisor
        }
        for(i in 1..10000) {
            for(m in monkeys.indices) {
                monkeys[m].process()
                for(item in monkeys[m].outList) {
                    monkeys[item.first].items.add(item.second)
                }
                monkeys[m].outList.clear()
            }
        }
        monkeys.sortByDescending { it.count }
        return monkeys[0].count.toLong() * monkeys[1].count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByEmptyLines("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInputSplitByEmptyLines("Day11")
    println(part1(input))
    println(part2(input))
}
