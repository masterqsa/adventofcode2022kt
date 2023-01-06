import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    fun isAdjacent(c1: Triple<Int, Int, Int>, c2: Triple<Int, Int, Int>): Boolean {
        var eq = 0
        var off1 = 0
        when (abs(c1.first - c2.first)) {
            0 -> eq+=1
            1 -> off1+=1
        }
        when (abs(c1.second - c2.second)) {
            0 -> eq+=1
            1 -> off1+=1
        }
        when (abs(c1.third - c2.third)) {
            0 -> eq+=1
            1 -> off1+=1
        }
        if (eq == 2 && off1 == 1) {
            return true
        }
        return false
    }
    fun part1(input: List<String>): Int {
        var cubes = mutableListOf<Triple<Int, Int, Int>>()
        for(l in input) {
            var coords = l.split(',').map { it.toInt() }
            cubes.add(Triple(coords[0], coords[1], coords[2]))
        }
        cubes.sortBy { it.first }
        var adj = 0
        for(i in 1 until cubes.size) {
            for(j in 0 .. i-1) {
                if (isAdjacent(cubes[i], cubes[j])) {
                    adj+=1
                }
            }
        }
        return cubes.size*6 - adj*2
    }



    fun part2(input: List<String>): Int {
        var cubes = mutableListOf<Triple<Int, Int, Int>>()
        var cset = mutableSetOf<Triple<Int, Int, Int>>()
        var minx: Int = 1000
        var maxx: Int = -1000
        var miny: Int = 1000
        var maxy: Int = -1000
        var minz: Int = 1000
        var maxz: Int = -1000
        for(l in input) {
            var coords = l.split(',').map { it.toInt() }
            cubes.add(Triple(coords[0], coords[1], coords[2]))
            cset.add(Triple(coords[0], coords[1], coords[2]))
            if (coords[0] < minx) minx = coords[0]
            if (coords[0] > maxx) maxx = coords[0]
            if (coords[1] < miny) miny = coords[1]
            if (coords[1] > maxy) maxy = coords[1]
            if (coords[2] < minz) minz = coords[2]
            if (coords[2] > maxz) maxz = coords[2]
        }
        cubes.sortBy { it.first }
        var adj = 0
        for(i in 1 until cubes.size) {
            for(j in 0 until i) {
                if (isAdjacent(cubes[i], cubes[j])) {
                    adj+=1
                }
            }
        }
        var locked = 0
        var suspects = mutableListOf<Triple<Int, Int, Int>>()
        for(x in minx+1 until maxx) {
            for(y in miny+1 until maxy) {
                for(z in minz+1 until maxz) {
                    var neighbors = 0
                    val c = Triple(x,y,z)
                    if (cset.contains(c)) continue
                    val c1 = Triple(x-1, y, z)
                    if (cset.contains(c1)) neighbors+=1
                    val c2 = Triple(x+1, y, z)
                    if (cset.contains(c2)) neighbors+=1
                    val c3 = Triple(x, y-1, z)
                    if (cset.contains(c3)) neighbors+=1
                    val c4 = Triple(x, y+1, z)
                    if (cset.contains(c4)) neighbors+=1
                    val c5 = Triple(x, y, z-1)
                    if (cset.contains(c5)) neighbors+=1
                    val c6 = Triple(x, y, z+1)
                    if (cset.contains(c6)) neighbors+=1
                    if (neighbors==6) locked+=1
                    if (neighbors > 0) {
                        suspects.add(c)
                    }
                }
            }
        }
        fun isEdge(c: Triple<Int, Int, Int>): Boolean {
            if (c.first <= minx || c.first >= maxx
                || c.second <= miny || c.second >= maxy
                || c.third <= minz || c.third >= maxz) return true
            return false
        }
        var innerSides = 0
        var visited = mutableSetOf<Triple<Int, Int, Int>>()
        var queue = LinkedList<Triple<Int, Int, Int>>()
        val dxyz = listOf<Triple<Int, Int, Int>>(Triple(-1, 0, 0), Triple(1, 0, 0),
            Triple(0, -1, 0),Triple(0, 1, 0),
            Triple(0, 0, -1),Triple(0, 0, 1))
        for(c in suspects) {
            if (!visited.contains(c)) {
                queue.add(c)
            }
            var adjsides = 0
            var exit = false
            while(!queue.isEmpty()) {
                var cube = queue.pop()
                visited.add(cube)
                var (x,y,z) = cube.toList()
                for(d in dxyz) {
                    val c1 = Triple(x + d.first, y + d.second, z+d.third)
                    if (cset.contains(c1)) {
                        adjsides += 1
                    } else {
                        if (isEdge(c1)) {
                            visited.add(c1)
                            exit = true
                        } else if (!visited.contains(c1)) {
                            visited.add(c1)
                            queue.add(c1)
                        }
                    }
                }
            }
            if (exit) {
                adjsides = 0
            }

            innerSides+=adjsides
        }
        // 4216 wrong, 2404 too low, 2549 incorrect, 2564 right
        return cubes.size*6 - adj*2 - innerSides
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    //check(part1(testInput) == 64)
    check(part2(testInput) == 58)

    val input = readInput("Day18")
    //println(part1(input))
    println(part2(input))
}
