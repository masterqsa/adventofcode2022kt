import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue


fun main() {
    data class Blizzard(var dir: Dir) {}
    fun getSurrounding(): List<Pair<Int, Int>> {
        return listOf ( Pair(0, -1),
            Pair(-1, 0), Pair(0, 0), Pair(1, 0),
            Pair(0, 1) )
    }
    fun part1(input: List<String>): Int {
        var height = input.size - 2;
        var width = input[0].length - 2;
        var map = mutableMapOf<Pair<Int, Int>, MutableList<Blizzard>>()
        for(j in input.indices) {
            var l = input[j]
            for (i in l.indices) {
                when(l[i]) {
                    '<' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.L))
                    '>' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.R))
                    '^' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.U))
                    'v' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.D))
                }
            }
        }
        var dp = Array<Array<Int>>(input[0].length-2) { _ -> Array<Int>(input.size-2) { _ -> -100000} }
        var best = mutableMapOf<Pair<Int, Int>, Int>()
        best[Pair(0,0)] = 1
        var step = 1

        fun moveBlizzards() {
            var newMap = mutableMapOf<Pair<Int, Int>, MutableList<Blizzard>>()
            for(bl in map) {
                var pos = bl.key
                for(b in bl.value) {
                    var dir = getDir(b.dir)
                    var newx = (pos.first + dir.first + width) % width
                    var newy = (pos.second + dir.second + height) % height

                    var newpos = Pair(newx, newy)
                    if (!newMap.containsKey(newpos)) {
                        newMap[newpos] = mutableListOf(b)
                    } else {
                        newMap[newpos]!!.add(b)
                    }
                    if (best.containsKey(newpos)) {
                        if (best[newpos]!! < step-1) {
                            best.remove(newpos)
                        }
                    }
                }
            }
            map = newMap
        }
        fun isValid(p: Pair<Int, Int>): Boolean {
            if (p.first < 0 || p.first > width-1 || p.second < 0 || p.second > height-1) {
                return false
            }
            return true
        }
        fun isTarget(p: Pair<Int, Int>): Boolean {
            if (p.first == width-1 && p.second == height-1) {
                return true
            }
            return false
        }
        fun printMap() {
            for(j in 0 .. input.size-3) {
                var s = "#"
                for (i in 0 .. input[0].length-3) {
                    var pos = Pair(i, j)
                    if (map.containsKey(pos)) {
                        s+= if (map[pos]!!.size > 1) map[pos]!!.size.toString() else map[pos]!![0].dir
                    } else {
                        s+="."
                    }
                }
                println(s+"#")
            }
            println()
        }
        moveBlizzards()
        while(true) {
            step+=1
            moveBlizzards()
            //printMap()
            var keysSnapshot = best.keys.toList()
            for(pos in keysSnapshot) {
                var options = getSurrounding()
                for (o in options) {
                    var c = Pair(pos.first + o.first, pos.second + o.second)
                    if (isValid(c) && !map.containsKey(c)) {
                        best[c] = step
                    }

                    if (isTarget(c)) {
                        return step+1
                    }
                }
            }
        }
        // 228, 264 is too low, 283 correct
        return 0
    }



    fun part2(input: List<String>): Int {
        var height = input.size - 2;
        var width = input[0].length - 2;
        var map = mutableMapOf<Pair<Int, Int>, MutableList<Blizzard>>()
        for(j in input.indices) {
            var l = input[j]
            for (i in l.indices) {
                when(l[i]) {
                    '<' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.L))
                    '>' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.R))
                    '^' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.U))
                    'v' -> map[Pair(i-1, j-1)] = mutableListOf( Blizzard(Dir.D))
                }
            }
        }
        var dp = Array<Array<Int>>(input[0].length-2) { _ -> Array<Int>(input.size-2) { _ -> -100000} }
        var best = mutableMapOf<Pair<Int, Int>, Int>()
        var step = 1
        var targets = listOf( Pair(width-1, height-1), Pair(0, 0),Pair(width-1, height-1))
        var starts = listOf( Pair(0, 0),Pair(width-1, height-1), Pair(0, 0))
        var currentStart = starts[0]
        var currentTarget = targets[0]


        fun moveBlizzards() {
            var newMap = mutableMapOf<Pair<Int, Int>, MutableList<Blizzard>>()
            for(bl in map) {
                var pos = bl.key
                for(b in bl.value) {
                    var dir = getDir(b.dir)
                    var newx = (pos.first + dir.first + width) % width
                    var newy = (pos.second + dir.second + height) % height

                    var newpos = Pair(newx, newy)
                    if (!newMap.containsKey(newpos)) {
                        newMap[newpos] = mutableListOf(b)
                    } else {
                        newMap[newpos]!!.add(b)
                    }
                    if (best.containsKey(newpos)) {
                        if (best[newpos]!! < step-1) {
                            best.remove(newpos)
                        }
                    }
                }
            }
            map = newMap
        }
        fun isValid(p: Pair<Int, Int>): Boolean {
            if (p.first < 0 || p.first > width-1 || p.second < 0 || p.second > height-1) {
                return false
            }
            return true
        }
        fun isTarget(p: Pair<Int, Int>): Boolean {
            if (p.first == currentTarget.first && p.second == currentTarget.second) {
                return true
            }
            return false
        }
        fun printMap() {
            for(j in 0 .. input.size-3) {
                var s = "#"
                for (i in 0 .. input[0].length-3) {
                    var pos = Pair(i, j)
                    if (map.containsKey(pos)) {
                        s+= if (map[pos]!!.size > 1) map[pos]!!.size.toString() else map[pos]!![0].dir
                    } else {
                        s+="."
                    }
                }
                println(s+"#")
            }
            println()
        }
        var totalSteps = 0

        for(i in 0 .. 2) {
            currentStart = starts[i]
            currentTarget = targets[i]
            best[currentStart] = 1
            step = 1
            var found = false
            moveBlizzards()
            while (!found) {
                step += 1
                moveBlizzards()
                //printMap()
                var keysSnapshot = best.keys.toList()
                for (pos in keysSnapshot) {
                    var options = getSurrounding()
                    for (o in options) {
                        var c = Pair(pos.first + o.first, pos.second + o.second)
                        if (isValid(c) && !map.containsKey(c)) {
                            best[c] = step
                        }

                        if (isTarget(c)) {
                            totalSteps+= (step + 1)
                            found = true
                        }
                    }
                }
                if (found) {
                    best = mutableMapOf<Pair<Int, Int>, Int>()
                }
            }
        }
        // 228, 264 is too low, 283 correct
        return totalSteps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 54)

    val input = readInput("Day25")
    println(part1(input))
    println(part2(input))
}
