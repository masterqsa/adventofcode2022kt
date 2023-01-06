import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun isStable(map: Map<Pair<Int, Int>, Char>, x: Int, y: Int): Boolean {
        return map.containsKey(Pair(x-1, y+1))
                && map.containsKey(Pair(x, y+1))
                && map.containsKey(Pair(x+1, y+1))
    }
    fun dropSand(map: MutableMap<Pair<Int, Int>, Char>, maxy: Int): Boolean {
        var x = 500
        var y = 0
        if (map.containsKey(Pair(x,y))) {
            return false
        }
        while(!isStable(map, x, y) && y<maxy) {
            if (!map.containsKey(Pair(x, y+1))) {
                y = y+1
            } else if (!map.containsKey(Pair(x-1, y+1))) {
                y = y+1
                x = x-1
            } else if (!map.containsKey(Pair(x+1, y+1))) {
                y = y+1
                x = x+1
            }
        }
        if (y < maxy) {
            map[Pair(x,y)] = 'o'
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var count = 0
        var maxy = 0
        var map = mutableMapOf<Pair<Int, Int>, Char>()
        for(l in input) {
            var points = l.split(" -> ")
            var start_x = points[0].split(',')[0].toInt()
            var start_y = points[0].split(',')[1].toInt()
            if (start_y > maxy) {
                maxy = start_y
            }
            for(i in 1 until points.size) {
                var dest_x = points[i].split(',')[0].toInt()
                var dest_y = points[i].split(',')[1].toInt()
                if (dest_y > maxy) {
                    maxy = dest_y
                }
                var sx = start_x
                var dx = dest_x
                var sy = start_y
                var dy = dest_y

                if (dest_x < start_x) {
                    dx = start_x
                    sx = dest_x
                }
                if (dest_y < start_y) {
                    dy = start_y
                    sy = dest_y
                }
                for(x in sx..dx) {
                    for(y in sy..dy) {
                        map[Pair(x,y)] = '#'
                    }
                }
                start_x = dest_x
                start_y = dest_y
            }
        }
        while(dropSand(map, maxy)) {
            count++
        }

        return count
    }



    fun part2(input: List<String>): Int {
        var count = 0
        var maxy = 0
        var map = mutableMapOf<Pair<Int, Int>, Char>()
        for(l in input) {
            var points = l.split(" -> ")
            var start_x = points[0].split(',')[0].toInt()
            var start_y = points[0].split(',')[1].toInt()
            if (start_y > maxy) {
                maxy = start_y
            }
            for(i in 1 until points.size) {
                var dest_x = points[i].split(',')[0].toInt()
                var dest_y = points[i].split(',')[1].toInt()
                if (dest_y > maxy) {
                    maxy = dest_y
                }
                var sx = start_x
                var dx = dest_x
                var sy = start_y
                var dy = dest_y

                if (dest_x < start_x) {
                    dx = start_x
                    sx = dest_x
                }
                if (dest_y < start_y) {
                    dy = start_y
                    sy = dest_y
                }
                for(x in sx..dx) {
                    for(y in sy..dy) {
                        map[Pair(x,y)] = '#'
                    }
                }
                start_x = dest_x
                start_y = dest_y
            }
        }
        var ultimate_y = maxy+2
        for(x in 500-ultimate_y-2..500+ultimate_y+2) {
            map[Pair(x, ultimate_y)] = '#'
        }
        while(dropSand(map, ultimate_y)) {
            count++
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
