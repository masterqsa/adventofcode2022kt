import java.lang.Math.*

fun main() {
    fun part1(input: List<String>): Int {
        var list = mutableListOf<Pair<Int, Boolean>>()
        for(l in input) {
            list.add(Pair(l.toInt(), false))

        }
        var moved = 0
        var current = 0
        while(moved < list.size) {
            current = current % list.size
            if (!list[current].second) {
                // move this one
                moved+=1
                var value = list[current].first
                if (value == 0) {
                    list[current] = Pair(value, true)
                    continue
                }
                var newpos = (current + value) + 3 * (list.size-1)
                newpos = newpos % (list.size-1)
                if (newpos == 0) {
                    list.removeAt(current)
                    list.add(Pair(value, true))
                } else if (newpos > current) {
                    list.removeAt(current)
                    list.add(newpos, Pair(value, true))
                } else {
                    list.removeAt(current)
                    list.add(newpos, Pair(value, true))

                }
            } else {
                current += 1
            }
        }

        var sum = 0
        var zeroIndex = -1
        for(i in list.indices) {
            if (list[i].first == 0) {
                zeroIndex = i
                break
            }
        }
        sum += (list[(zeroIndex + 1000) % list.size].first
            + list[(zeroIndex + 2000) % list.size].first
            + list[(zeroIndex + 3000) % list.size].first)

        //7284 is incorrect, 8494 too high, -6317 incorrect
        return sum
    }



    fun part2(input: List<String>): Long {
        var key = 811589153L
        var list = mutableListOf<Pair<Long, Int>>()
        var originalList = mutableListOf<Pair<Long, Int>>()
        var order = 0
        for(l in input) {
            list.add(Pair(l.toInt() * key, order))
            originalList.add(Pair(l.toInt() * key, order))
            order+=1

        }
        for(step in 1..10) {
            for(order in 0 until list.size) {
                var current = list.indexOf(originalList[order])
                var value = list[current].first
                if (value == 0L) {
                    list[current] = Pair(value, order)
                    continue
                }
                var newpos = (current + value) + 2*key * (list.size - 1)
                newpos = newpos % (list.size - 1)
                if (newpos == 0L) {
                    list.removeAt(current)
                    list.add(Pair(value, order))
                } else {
                    list.removeAt(current)
                    list.add(newpos.toInt(), Pair(value, order))
                }
            }
        }

        var sum = 0L
        var zeroIndex = -1
        for(i in list.indices) {
            if (list[i].first == 0L) {
                zeroIndex = i
                break
            }
        }
        sum += (list[(zeroIndex + 1000) % list.size].first
                + list[(zeroIndex + 2000) % list.size].first
                + list[(zeroIndex + 3000) % list.size].first)

        return sum

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 1623178306L)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
