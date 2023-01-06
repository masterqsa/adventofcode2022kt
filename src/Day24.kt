import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

enum class Dirs {
    N, S, W, E
}
fun getSide(d: Dirs): List<Pair<Int, Int>> {
    when (d) {
        Dirs.N -> return listOf<Pair<Int, Int>> ( Pair(-1, -1), Pair(0, -1), Pair(1, -1) )
        Dirs.S -> return listOf<Pair<Int, Int>> ( Pair(-1, 1), Pair(0, 1), Pair(1, 1) )
        Dirs.W -> return listOf<Pair<Int, Int>> ( Pair(-1, -1), Pair(-1, 0), Pair(-1, 1) )
        Dirs.E -> return listOf<Pair<Int, Int>> ( Pair(1, -1), Pair(1, 0), Pair(1, 1) )
    }
}
fun getSurrounding(): List<Pair<Int, Int>> {
    return listOf ( Pair(-1, -1), Pair(0, -1), Pair(1, -1),
        Pair(-1, 0), Pair(1, 0),
        Pair(-1, 1), Pair(0, 1), Pair(1, 1) )
}
fun main() {
    fun part1(input: List<String>): Int {
        var map = mutableSetOf<Pair<Int, Int>>()
        for(j in input.indices) {
            for(i in input[j].indices) {
                if (input[j][i] == '#') {
                    map.add(Pair(i, j))
                }
            }
        }
        var dirs = LinkedList<Dirs>()
        dirs.add(Dirs.N)
        dirs.add(Dirs.S)
        dirs.add(Dirs.W)
        dirs.add(Dirs.E)
        fun checkSurround(e: Pair<Int, Int>): Boolean {
            for(d in getSurrounding()) {
                if (map.contains(Pair(e.first+d.first, e.second+d.second))) {
                    return false
                }
            }
            return true
        }
        fun checkSide(e: Pair<Int, Int>, s: List<Pair<Int, Int>>): Boolean {
            for(c in s) {
                if (map.contains(Pair(e.first + c.first, e.second+c.second))) {
                    return false
                }
            }
            return true
        }
        for (step in 1 .. 10) {
            var next = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
            for(elf in map) {
                if (checkSurround(elf)) {
                    next[elf] = mutableSetOf(elf)
                    continue
                }
                var moved = false
                for(d in dirs) {
                    var options = getSide(d)
                    if (checkSide(elf, options)) {
                        var coord = Pair(elf.first+options[1].first, elf.second + options[1].second)
                        if (!next.containsKey(coord)) {
                            next[coord] = mutableSetOf(elf)
                        } else {
                            next[coord]!!.add(elf)
                        }
                        moved = true
                        break
                    }
                }
                if (!moved) {
                    next[elf] = mutableSetOf(elf)
                }
            }
            map = next.filter { it.value.size == 1 }.keys.toMutableSet()
            for (conflicts in next.filter { it.value.size > 1 }.values) {
                for(e in conflicts) {
                    map.add(e)
                }
            }

            var f = dirs.pollFirst()
            dirs.addLast(f)
        }
        var minx = 100000
        var maxx = -100000
        var miny = 100000
        var maxy = -100000
        for (e in map) {
            if (e.first < minx) minx = e.first
            if (e.first > maxx) maxx = e.first
            if (e.second < miny) miny = e.second
            if (e.second > maxy) maxy = e.second
        }
        return (maxx - minx + 1) * (maxy - miny + 1) - map.size
    }



    fun part2(input: List<String>): Int {
        var map = mutableSetOf<Pair<Int, Int>>()
        for(j in input.indices) {
            for(i in input[j].indices) {
                if (input[j][i] == '#') {
                    map.add(Pair(i, j))
                }
            }
        }
        var dirs = LinkedList<Dirs>()
        dirs.add(Dirs.N)
        dirs.add(Dirs.S)
        dirs.add(Dirs.W)
        dirs.add(Dirs.E)
        fun checkSurround(e: Pair<Int, Int>): Boolean {
            for(d in getSurrounding()) {
                if (map.contains(Pair(e.first+d.first, e.second+d.second))) {
                    return false
                }
            }
            return true
        }
        fun checkSide(e: Pair<Int, Int>, s: List<Pair<Int, Int>>): Boolean {
            for(c in s) {
                if (map.contains(Pair(e.first + c.first, e.second+c.second))) {
                    return false
                }
            }
            return true
        }
        var step = 1
        var movement = true
        while (movement) {
            movement = false
            var next = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
            for(elf in map) {
                if (checkSurround(elf)) {
                    next[elf] = mutableSetOf(elf)
                    continue
                }
                var moved = false
                for(d in dirs) {
                    var options = getSide(d)
                    if (checkSide(elf, options)) {
                        var coord = Pair(elf.first+options[1].first, elf.second + options[1].second)
                        if (!next.containsKey(coord)) {
                            next[coord] = mutableSetOf(elf)
                        } else {
                            next[coord]!!.add(elf)
                        }
                        movement = true
                        moved = true
                        break
                    }
                }
                if (!moved) {
                    next[elf] = mutableSetOf(elf)
                }
            }
            map = next.filter { it.value.size == 1 }.keys.toMutableSet()
            for (conflicts in next.filter { it.value.size > 1 }.values) {
                for(e in conflicts) {
                    map.add(e)
                }
            }
            if (!movement) {
                break
            }

            var f = dirs.pollFirst()
            dirs.addLast(f)
            step+=1
        }

        return step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day24_test")
    check(part1(testInput) == 110)
    check(part2(testInput) == 20)

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
