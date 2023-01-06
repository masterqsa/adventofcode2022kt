import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var map = mutableListOf<String>()
        var visited = mutableSetOf<Pair<Int, Int>>()
        var q = LinkedList<Pair<Int, Int>>()
        var dest: Pair<Int, Int> = Pair(0,0)
        var maxy = input.size-1
        var maxx = input[0].length-1
        for(i in input.indices) {
            for(j in input[i].indices) {
                if (input[i][j] == 'S') {
                    q.add(Pair(i,j))
                    visited.add(Pair(i,j))
                } else if (input[i][j] == 'E') {
                    dest = Pair(i,j)
                }
            }
            map.add(input[i].replace('S','a').replace('E', 'z'))
        }
        var found = false
        var steps = 0
        var distances = mutableMapOf<Pair<Int, Int>, Int>()
        var dirs: List<Pair<Int, Int>> = listOf(Pair(-1,0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
        while(!found) {
            var newq = LinkedList<Pair<Int, Int>>()
            steps+=1
            while (!q.isEmpty()) {
                var c = q.pop()
                for(d in dirs) {
                    var y = c.first + d.first
                    var x = c.second + d.second
                    if (x >= 0 && y >= 0 && x<= maxx && y<= maxy) {
                        if (!visited.contains(Pair(y, x))) {
                             if (map[y][x]-map[c.first][c.second] <= 1) {
                                 if (y == dest.first && x == dest.second) {
                                     found = true
                                     break
                                 }
                                newq.add(Pair(y,x))
                                visited.add(Pair(y,x))
                                distances[Pair(y,x)] = steps
                            }
                        }
                    }
                }
            }
            q = newq
        }

        return steps
    }



    fun part2(input: List<String>): Int {
        var map = mutableListOf<String>()
        var visited = mutableSetOf<Pair<Int, Int>>()
        var q = LinkedList<Pair<Int, Int>>()
        var maxy = input.size-1
        var maxx = input[0].length-1
        for(i in input.indices) {
            for(j in input[i].indices) {
                if (input[i][j] == 'E') {
                    q.add(Pair(i,j))
                    visited.add(Pair(i,j))
                }
            }
            map.add(input[i].replace('S','a').replace('E', 'z'))
        }
        var found = false
        var steps = 0
        var distances = mutableMapOf<Pair<Int, Int>, Int>()
        var dirs: List<Pair<Int, Int>> = listOf(Pair(-1,0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
        while(!found) {
            var newq = LinkedList<Pair<Int, Int>>()
            steps+=1
            while (!q.isEmpty()) {
                var c = q.pop()
                for(d in dirs) {
                    var y = c.first + d.first
                    var x = c.second + d.second
                    if (x >= 0 && y >= 0 && x<= maxx && y<= maxy) {
                        if (!visited.contains(Pair(y, x))) {
                            if (map[c.first][c.second]-map[y][x] <= 1) {
                                if (map[y][x] == 'a') {
                                    found = true
                                    break
                                }
                                newq.add(Pair(y,x))
                                visited.add(Pair(y,x))
                                distances[Pair(y,x)] = steps
                            }
                        }
                    }
                }
            }
            q = newq
        }

        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
