import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    fun combineRanges(r: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
        var ret = mutableListOf<Pair<Int, Int>>()
        var current = r[0]
        for(i in 1 until  r.size) {
            if (current.second < r[i].first) {
                ret.add(current)
                current = r[i]
            } else {
                current = Pair(current.first, Math.max(current.second, r[i].second))
            }
        }
        ret.add(current)
        return ret
    }
    fun part1(input: List<String>, ypos:Int): Int {
        var map = mutableMapOf<Pair<Int, Int>, Int>()
        for(l in input) {
            //Sensor at x=2483411, y=3902983: closest beacon is at x=2289579, y=3633785
            val match = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)").find(l)!!
            val (sxstr, systr, bxstr, bystr) = match.destructured
            val sx = sxstr.toInt()
            val sy = systr.toInt()
            val bx = bxstr.toInt()
            val by = bystr.toInt()
            map[Pair(sx, sy)] = Math.abs(sx-bx) + Math.abs(sy-by)
            if (!map.containsKey(Pair(bx, by))) {
                map[Pair(bx, by)] = 0
            }
        }
        var ranges = mutableListOf<Pair<Int, Int>>()
        for(i in map.keys) {
            var dx = map[i]!! - Math.abs(i.second - ypos)
            if (dx > 0) {
                ranges.add(Pair(i.first-dx, i.first+dx))
            }
        }
        ranges.sortBy { it.first }
        ranges = combineRanges(ranges)
        var sum = 0
        for(r in ranges) {
            sum += r.second - r.first
        }
        return sum
    }



    fun part2(input: List<String>, maxCoord: Int): Long {
        var minCoord = 0
        var map = mutableMapOf<Pair<Int, Int>, Int>()
        for(l in input) {
            //Sensor at x=2483411, y=3902983: closest beacon is at x=2289579, y=3633785
            val match = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)").find(l)!!
            val (sxstr, systr, bxstr, bystr) = match.destructured
            val sx = sxstr.toInt()
            val sy = systr.toInt()
            val bx = bxstr.toInt()
            val by = bystr.toInt()
            map[Pair(sx, sy)] = Math.abs(sx-bx) + Math.abs(sy-by)
            if (!map.containsKey(Pair(bx, by))) {
                map[Pair(bx, by)] = 0
            }
        }
        var point: Pair<Int, Int>
        for(ypos in 0..maxCoord) {
            var ranges = mutableListOf<Pair<Int, Int>>()
            for (i in map.keys) {
                var dx = map[i]!! - Math.abs(i.second - ypos)
                if (dx > 0) {
                    ranges.add(Pair(i.first - dx, i.first + dx))
                }
            }
            ranges.sortBy { it.first }
            ranges = combineRanges(ranges)
            var current = 0
            var i = 0
            while(current <= maxCoord && i < ranges.size) {
                if (current >= ranges[i].first && current <= ranges[i].second) {
                    current = ranges[i].second+1
                }
                i+=1
            }
            if (current <= maxCoord) {
                point = Pair(current, ypos)
                // 106047218
                return current * 4000000L + ypos
            }
        }
        return 0L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput, 10) == 26)
    check(part2(testInput, 20) == 56000011L)

    val input = readInput("Day15")
    println(part1(input, 2000000))
    println(part2(input, 4000000))
}
