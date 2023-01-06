import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue
import kotlin.Exception

enum class Dir {
    L, R, U, D
}
data class State(var x: Int, var y: Int, var d: Dir, var side: Int) {

}
fun getDir(d: Dir): Pair<Int, Int> {
    when (d) {
        Dir.L -> return Pair(-1, 0)
        Dir.R -> return Pair(1, 0)
        Dir.U -> return Pair(0, -1)
        Dir.D -> return Pair(0, 1)
    }
}
fun rotate(d: Dir, turn: Char): Dir {
    when (d) {
        Dir.L -> return if (turn == 'R') Dir.U else Dir.D
        Dir.R -> return if (turn == 'R') Dir.D else Dir.U
        Dir.U -> return if (turn == 'R') Dir.R else Dir.L
        Dir.D -> return if (turn == 'R') Dir.L else Dir.R
    }
}
fun getDirValue(d: Dir): Int {
    when (d) {
        Dir.L -> return 2
        Dir.R -> return 0
        Dir.U -> return 3
        Dir.D -> return 1
    }
}
fun getSide(x: Int, y: Int): Int {
    if (x in 0..49) {
        if (y in 100..149) {
            return 4
        }
        if (y in 150 .. 199) {
            return 6
        }
    }
    if (x in 50 .. 99) {
        if (y in 0 .. 49) {
            return 1
        }
        if (y in 50 .. 99) {
            return 3
        }
        if (y in 100 .. 149) {
            return 5
        }
    }
    if (x in 100 .. 149) {
        if (y in 0 .. 49) {
            return 2
        }
    }
    return 0
}
fun coordTransform(s: State): State {
    var dxy = getDir(s.d)
    var newx = s.x + dxy.first
    var newy = s.y + dxy.second
    var side = getSide(newx, newy)
    if (s.side == side) {
        return State(newx, newy, s.d, s.side)
    } else {
        // Simple cases
        if (s.side == 1 && s.d == Dir.R) {
            return State(newx, newy, s.d, 2)
        }
        if (s.side == 2 && s.d == Dir.L) {
            return State(newx, newy, s.d, 1)
        }
        if (s.side == 1 && s.d == Dir.D) {
            return State(newx, newy, s.d, 3)
        }
        if (s.side == 3 && s.d == Dir.U) {
            return State(newx, newy, s.d, 1)
        }
        if (s.side == 3 && s.d == Dir.D) {
            return State(newx, newy, s.d, 5)
        }
        if (s.side == 5 && s.d == Dir.U) {
            return State(newx, newy, s.d, 3)
        }
        if (s.side == 4 && s.d == Dir.R) {
            return State(newx, newy, s.d, 5)
        }
        if (s.side == 5 && s.d == Dir.L) {
            return State(newx, newy, s.d, 4)
        }
        if (s.side == 4 && s.d == Dir.D) {
            return State(newx, newy, s.d, 6)
        }
        if (s.side == 6 && s.d == Dir.U) {
            return State(newx, newy, s.d, 4)
        }
        // Transformations
        if (s.side == 1 && s.d == Dir.U) {
            return State(0, 150+(s.x-50), Dir.R, 6)
        }
        if (s.side == 6 && s.d == Dir.L) {
            return State(50+(s.y-150), 0, Dir.D, 1)
        }
        if (s.side == 1 && s.d == Dir.L) {
            return State(0, 149-s.y , Dir.R, 4)
        }
        if (s.side == 4 && s.d == Dir.L) {
            return State(50, 149-s.y, Dir.R, 1)
        }
        if (s.side == 2 && s.d == Dir.U) {
            return State(s.x-100, 199 , Dir.U, 6)
        }
        if (s.side == 6 && s.d == Dir.D) {
            return State(s.x+100, 0, Dir.D, 2)
        }
        if (s.side == 2 && s.d == Dir.R) {
            return State(99, 149-s.y , Dir.L, 5)
        }
        if (s.side == 5 && s.d == Dir.R) {
            return State(149, 149-s.y, Dir.L, 2)
        }
        if (s.side == 3 && s.d == Dir.L) {
            return State(s.y-50, 100 , Dir.D, 4)
        }
        if (s.side == 4 && s.d == Dir.U) {
            return State(50, s.x+50, Dir.R, 3)
        }
        if (s.side == 2 && s.d == Dir.D) {
            return State(99, 50+(s.x-100) , Dir.L, 3)
        }
        if (s.side == 3 && s.d == Dir.R) {
            return State(s.y+50, 49, Dir.U, 2)
        }
        if (s.side == 5 && s.d == Dir.D) {
            return State(49, 150+(s.x-50) , Dir.L, 6)
        }
        if (s.side == 6 && s.d == Dir.R) {
            return State(s.y-100, 149, Dir.U, 5)
        }
    }
    throw Exception("Impossible")
}
fun Char.repeat(count: Int): String = this.toString().repeat(count)
fun main() {
    fun part1(input: List<String>): Int {
//Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
        var map = input[0].split("\n", "\r\n").toMutableList()
        var horstart = Array<Int>(map.size) { _-> 0}
        var horend = Array<Int>(map.size) { _-> 0}

        var maxlen = 0
        for(j in map.indices) {
            if (map[j].length > maxlen) {
                maxlen = map[j].length
            }
        }
        for(j in map.indices) {
            if (map[j].length < maxlen) {
                map[j] = map[j]+' '.repeat(maxlen-map[j].length)
            }
        }
        var verstart = Array<Int>(maxlen) { _-> 0}
        var verend = Array<Int>(maxlen) { _-> 0}
        for(j in map.indices) {
            var line = map[j]
            for(i in line.indices) {
                if (line[i] == '.' || line[i] == '#') {
                    if (j == 0) {
                        verstart[i] = j
                    } else if (j == map.size-1) {
                        verend[i] = j
                    } else {
                        if (map[j-1][i] == ' ') {
                            verstart[i] = j
                        }
                        if (map[j+1][i] == ' ') {
                            verend[i] = j
                        }
                    }
                    if (i == 0) {
                        horstart[j] = i
                    } else if (i == line.length-1) {
                        horend[j] = i
                    } else {
                        if (map[j][i-1] == ' ') {
                            horstart[j] = i
                        }
                        if (map[j][i+1] == ' ') {
                            horend[j] = i
                        }
                    }
                }
            }
        }
        var x = 0
        var y = 0
        var dir = Dir.R
        for(i in map[0].indices) {
            if (map[0][i] == '.') {
                x = i
                break
            }
        }
        fun move(loc: Triple<Int, Int, Dir>, d: Char, steps: Int): Triple<Int, Int, Dir> {
            var x = loc.first
            var y = loc.second
            var dir = loc.third
            if (d == 'L' || d == 'R') {
                dir = rotate(dir, d)
            }
            var dxy = getDir(dir)
            for(i in 0 until steps) {
                var newx = x + dxy.first
                var newy = y + dxy.second
                if (dxy.first == 1) {
                    if (newx > map[0].length-1 || map[newy][newx] == ' ') {
                        newx = horstart[y]
                    }
                }
                if (dxy.first == -1) {
                    if (newx < 0 || map[newy][newx] == ' ') {
                        newx = horend[y]
                    }
                }
                if (dxy.second == 1) {
                    if (newy > map.size-1 || map[newy][newx] == ' ') {
                        newy = verstart[x]
                    }
                }
                if (dxy.second == -1) {
                    if (newy < 0 || map[newy][newx] == ' ') {
                        newy = verend[x]
                    }
                }
                if (map[newy][newx] == '#') {
                    newx = x
                    newy = y
                    break
                } else {
                    x = newx
                    y = newy
                }
            }
            return Triple(x, y, dir)
        }
        val match = Regex("(L?R?\\d+)").findAll(input[1])!!
        var dirs = match.toMutableList()
        for(res in dirs) {
            var result = res.destructured.component1()
            var d = ' '
            var steps = 0
            if (result[0] == 'L' || result[0] == 'R') {
                d = result[0]
                steps = result.substring(1, result.length).toInt()
            } else {
                steps = result.toInt()
            }
            var newLoc = move(Triple(x, y, dir), d, steps)
            x = newLoc.first
            y = newLoc.second
            dir = newLoc.third
        }

        return 1000*(y+1) + 4*(x+1) + getDirValue(dir)
    }

    fun part2(input: List<String>): Int {
        var map = input[0].split("\n", "\r\n").toMutableList()
        var horstart = Array<Int>(map.size) { _-> 0}
        var horend = Array<Int>(map.size) { _-> 0}

        var maxlen = 0
        for(j in map.indices) {
            if (map[j].length > maxlen) {
                maxlen = map[j].length
            }
        }
        for(j in map.indices) {
            if (map[j].length < maxlen) {
                map[j] = map[j]+' '.repeat(maxlen-map[j].length)
            }
        }
        var verstart = Array<Int>(maxlen) { _-> 0}
        var verend = Array<Int>(maxlen) { _-> 0}
        for(j in map.indices) {
            var line = map[j]
            for(i in line.indices) {
                if (line[i] == '.' || line[i] == '#') {
                    if (j == 0) {
                        verstart[i] = j
                    } else if (j == map.size-1) {
                        verend[i] = j
                    } else {
                        if (map[j-1][i] == ' ') {
                            verstart[i] = j
                        }
                        if (map[j+1][i] == ' ') {
                            verend[i] = j
                        }
                    }
                    if (i == 0) {
                        horstart[j] = i
                    } else if (i == line.length-1) {
                        horend[j] = i
                    } else {
                        if (map[j][i-1] == ' ') {
                            horstart[j] = i
                        }
                        if (map[j][i+1] == ' ') {
                            horend[j] = i
                        }
                    }
                }
            }
        }
        var x = 0
        var y = 0
        var dir = Dir.R
        for(i in map[0].indices) {
            if (map[0][i] == '.') {
                x = i
                break
            }
        }

        val match = Regex("(L?R?\\d+)").findAll(input[1])!!
        var dirs = match.toMutableList()
        var state = State(x, y, dir, 1)
        for(res in dirs) {
            var result = res.destructured.component1()
            var d = ' '
            var steps = 0
            if (result[0] == 'L' || result[0] == 'R') {
                d = result[0]
                steps = result.substring(1, result.length).toInt()
            } else {
                steps = result.toInt()
            }
            if (d == 'L' || d == 'R') {
                state.d = rotate(state.d, d)
            }
            for(i in 0 until steps) {
                var newstate = coordTransform(state)
                //println("x="+newstate.x.toString()+", y="+newstate.y.toString()+", dir="+newstate.d.toString())
                if (map[newstate.y][newstate.x] == '#') {
                    break
                }
                state = newstate
                println("x="+state.x.toString()+", y="+state.y.toString()+", dir="+state.d.toString())
            }
        }
        // 1252, 25330 is too low
        return 1000*(state.y+1) + 4*(state.x+1) + getDirValue(state.d)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByEmptyLinesNoTrim("Day23_test")
    //check(part1(testInput) == 6032)
    //check(part2(testInput) == 5031)

    val input = readInputSplitByEmptyLinesNoTrim("Day23")
    //println(part1(input))
    println(part2(input))
}
