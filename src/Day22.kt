import java.lang.Math.*
fun main() {
    data class Monkey(var name: String, var value: Long?,
                      var args: Pair<String, String>?, var op: Char?,
                        var humn: Boolean = false) {}
    fun part1(input: List<String>): Long {
        var monkeys = mutableMapOf<String, Monkey>()
        for(l in input) {
            var split = l.split(": ")
            var op: Char? = null
            var value: Long? = null
            var args: Pair<String, String>? = null
            if (split[1].length == 11) {
                var arg1 = split[1].substring(0, 4)
                op = split[1][5]
                var arg2 = split[1].substring(7, split[1].length)
                args = Pair(arg1, arg2)
            } else {
                value = split[1].toLong()
            }
            monkeys[split[0]] = Monkey(split[0], value, args, op, split[0]=="humn")
        }
        fun getValue(m: Monkey): Long {
            if (m.value != null) {
                return m.value!!
            }
            when(m.op!!) {
                '+' -> return monkeys[m.args!!.first]!!.value!! + monkeys[m.args!!.second]!!.value!!
                '-' -> return monkeys[m.args!!.first]!!.value!! - monkeys[m.args!!.second]!!.value!!
                '*' -> return monkeys[m.args!!.first]!!.value!! * monkeys[m.args!!.second]!!.value!!
                '/' -> return monkeys[m.args!!.first]!!.value!! / monkeys[m.args!!.second]!!.value!!
            }
            throw Exception("Unexpected")
        }
        var stack = ArrayDeque<String>()
        stack.addLast("root")
        while(!stack.isEmpty()) {
            var current = stack.last()
            var m = monkeys[current]!!
            var ready = true
            if (monkeys[m.args!!.first]!!.value == null) {
                stack.addLast(m.args!!.first)
                ready = false
            }
            if (monkeys[m.args!!.second]!!.value == null) {
                stack.addLast(m.args!!.second)
                ready = false
            }
            if (ready) {
                stack.removeLast()
                m.value = getValue(m)
                monkeys[current] = m
            }
        }
        // -393342880 wrong
        return monkeys["root"]!!.value!!
    }



    fun part2(input: List<String>): Long {
        var monkeys = mutableMapOf<String, Monkey>()
        for(l in input) {
            var split = l.split(": ")
            var op: Char? = null
            var value: Long? = null
            var args: Pair<String, String>? = null
            if (split[1].length == 11) {
                var arg1 = split[1].substring(0, 4)
                op = split[1][5]
                var arg2 = split[1].substring(7, split[1].length)
                args = Pair(arg1, arg2)
            } else {
                value = split[1].toLong()
            }
            monkeys[split[0]] = Monkey(split[0], value, args, op, split[0]=="humn")
        }
        fun getValue(m: Monkey): Long {
            if (m.value != null) {
                return m.value!!
            }
            when(m.op!!) {
                '+' -> return monkeys[m.args!!.first]!!.value!! + monkeys[m.args!!.second]!!.value!!
                '-' -> return monkeys[m.args!!.first]!!.value!! - monkeys[m.args!!.second]!!.value!!
                '*' -> return monkeys[m.args!!.first]!!.value!! * monkeys[m.args!!.second]!!.value!!
                '/' -> return monkeys[m.args!!.first]!!.value!! / monkeys[m.args!!.second]!!.value!!
            }
            throw Exception("Unexpected")
        }
        var stack = ArrayDeque<String>()
        stack.addLast("root")
        while(!stack.isEmpty()) {
            var current = stack.last()
            var m = monkeys[current]!!
            var ready = true
            if (monkeys[m.args!!.first]!!.value == null) {
                stack.addLast(m.args!!.first)
                ready = false
            }
            if (monkeys[m.args!!.second]!!.value == null) {
                stack.addLast(m.args!!.second)
                ready = false
            }
            if (monkeys[m.args!!.first]!!.humn || monkeys[m.args!!.second]!!.humn) {
                m.humn = true
                monkeys[current] = m
            }
            if (ready) {
                stack.removeLast()
                m.value = getValue(m)
                monkeys[current] = m
            }
        }
        // -393342880 wrong
        var val1 = monkeys[monkeys["root"]!!.args!!.first]!!.value!!
        var val2 = monkeys[monkeys["root"]!!.args!!.second]!!.value!!
        var current: String = "root"
        var desired: Long = 0L
        if (monkeys[monkeys["root"]!!.args!!.first]!!.humn) {
            desired = val2
            current = monkeys["root"]!!.args!!.first
        } else {
            desired = val1
            current = monkeys["root"]!!.args!!.second
        }
        while(current != "humn") {
            var m = monkeys[current]!!
            var first = monkeys[m.args!!.first]!!
            var second = monkeys[m.args!!.second]!!
            if (first.humn) {
                current = first.name
            } else {
                current = second.name
            }
            when(m.op!!) {
                '+' -> {
                    if (first.humn) {
                        desired = desired - second.value!!
                    } else {
                        desired = desired - first.value!!
                    }
                }
                '-' -> {
                    if (first.humn) {
                        desired = desired + second.value!!
                    } else {
                        desired = first.value!! - desired
                    }
                }
                '*' -> {
                    if (first.humn) {
                        desired = desired / second.value!!
                    } else {
                        desired = desired / first.value!!
                    }
                }
                '/' -> {
                    if (first.humn) {
                        desired = desired * second.value!!
                    } else {
                        desired = first.value!! / desired
                    }
                }
            }
        }
        return desired
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}
