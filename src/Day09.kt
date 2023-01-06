import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        fun catch(h: Pair<Int, Int>, t: Pair<Int, Int>): Pair<Int, Int> {
            var ret = t
            if ((h.first-t.first).absoluteValue <= 1 && (h.second-t.second).absoluteValue <= 1) {
                return ret
            } else {
                // need to compensate
                if ((h.first-t.first).absoluteValue == 2) {
                    ret = Pair((h.first+t.first)/2, h.second)
                } else {
                    ret = Pair(h.first, (h.second+t.second)/2)
                }
            }
            return ret
        }
        var visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(Pair(0,0))
        var h = Pair(0,0)
        var t = Pair(0,0)
        for(l in input) {
            var split = l.split(' ')
            var dir = split[0]
            var steps = split[1].toInt()
            for(i in 1..steps) {
                when (dir) {
                    "U" -> h = Pair(h.first, h.second+1)
                    "D" -> h = Pair(h.first, h.second-1)
                    "L" -> h = Pair(h.first-1, h.second)
                    "R" -> h = Pair(h.first+1, h.second)
                }
                t = catch(h, t)
                if (!visited.contains(t)) {
                    visited.add(t)
                }
            }
        }

        return visited.count()
    }



    fun part2(input: List<String>): Int {
        fun catch(h: Pair<Int, Int>, t: Pair<Int, Int>): Pair<Int, Int> {
            var ret = t
            if ((h.first-t.first).absoluteValue <= 1 && (h.second-t.second).absoluteValue <= 1) {
                return ret
            } else {
                // need to compensate
                if ((h.first-t.first).absoluteValue == 2) {
                    if ((h.second-t.second).absoluteValue == 2) {
                        ret = Pair((h.first + t.first) / 2, (h.second + t.second) / 2)
                    } else {
                        ret = Pair((h.first + t.first) / 2, h.second)
                    }
                } else {
                    if ((h.first-t.first).absoluteValue == 2) {
                        ret = Pair((h.first + t.first) / 2, (h.second + t.second) / 2)
                    } else {
                        ret = Pair(h.first, (h.second + t.second) / 2)
                    }
                }
            }
            return ret
        }
        var visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(Pair(0,0))
        var knots = Array<Pair<Int, Int>>(10) { _ -> Pair(0,0)}
        var maxx = 0
        var maxy = 0
        var minx = 0
        var miny = 0
        for(l in input) {
            var split = l.split(' ')
            var dir = split[0]
            var steps = split[1].toInt()
            for(i in 1..steps) {
                var h = knots[0]
                when (dir) {
                    "U" -> h = Pair(h.first, h.second+1)
                    "D" -> h = Pair(h.first, h.second-1)
                    "L" -> h = Pair(h.first-1, h.second)
                    "R" -> h = Pair(h.first+1, h.second)
                }
                knots[0] = h
                if (minx > h.first) {
                    minx = h.first
                }
                if (maxx < h.first) {
                    maxx = h.first
                }
                if (miny > h.second) {
                    miny = h.second
                }
                if (maxy < h.second) {
                    maxy = h.second
                }
                for (j in 1..9) {
                    knots[j] = catch(knots[j-1], knots[j])
                }
                var t = knots[9]
                if (!visited.contains(t)) {
                    visited.add(t)
                }
            }
            //println(dir + " " + steps)
            for(y in miny..maxy){
                var s = ""
                for(x in minx..maxx) {
                    var c = "."
                    if (x == 0 && y == 0) {
                        c = "s"
                    }
                    for(p in 9 downTo 0) {
                        if(knots[p] == Pair(x,y)) {
                            c = p.toString()
                            if (p == 0) {
                                c = "H"
                            }
                        }
                    }
                    s += c
                }
                //println(s)
            }
        }
        var count = visited.count()
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    //check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
