import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min

fun main() {
    fun part1(input: List<String>): Int {
        fun setVis(m: Array<Array<Int>>, mvis: Array<Array<Boolean>>, i: Int, j: Int, max: Int): Int {
            var ret = max
            if (m[i][j] > max) {
                mvis[i][j] = true
                ret = m[i][j]
            }
            return ret
        }
        var count = 0
        var imax = input.size
        var jmax = input[0].length
        var m = Array<Array<Int>>(imax) { _ -> Array<Int>(jmax) { _ -> 0 }}
        for(i in input.indices) {
            var line = input[i]
            for(j in line.indices) {
                m[i][j] = line[j].digitToInt()
            }
        }
        var mvis = Array<Array<Boolean>>(imax) { _ -> Array<Boolean>(jmax) { _ -> false }}
        for(i in 0 until imax) {
            var max = -1
            for(j in 0 until jmax) {
                max = setVis(m, mvis, i, j, max)
            }
            max = -1
            for(j in jmax-1 downTo  0) {
                max = setVis(m, mvis, i, j, max)
            }
        }
        for(j in 0 until jmax) {
            var max = -1
            for(i in 0 until imax) {
                max = setVis(m, mvis, i, j, max)
            }
            max = -1
            for(i in imax-1 downTo  0) {
                max = setVis(m, mvis, i, j, max)
                if (mvis[i][j]) {
                    count+=1
                }
            }
        }


        return count
    }



    fun part2(input: List<String>): Int {
        var max = 0
        var imax = input.size
        var jmax = input[0].length
        var m = Array<Array<Int>>(imax) { _ -> Array<Int>(jmax) { _ -> 0 }}
        for(i in input.indices) {
            var line = input[i]
            for(j in line.indices) {
                m[i][j] = line[j].digitToInt()
            }
        }
        var mvis = Array<Array<Array<Int>>>(imax) { _ -> Array<Array<Int>>(jmax) { _ -> Array<Int>(4) { _ -> 0} }}
        var t = 0
        for(i in 1 until imax-1) {
            for(j in 1 until jmax-1) {
                t = 0
                var count = 0
                for(x in i-1 downTo 0) {
                   if (m[i][j] > m[x][j]) {
                       count+=1
                   } else {
                       count+=1
                       break
                   }
                }
                mvis[i][j][t] = count
                t = 1
                count = 0
                for(x in i+1 until imax) {
                    if (m[i][j] > m[x][j]) {
                        count+=1
                    } else {
                        count+=1
                        break
                    }
                }
                mvis[i][j][t] = count
                t = 2
                count = 0
                for(y in j-1 downTo 0) {
                    if (m[i][j] > m[i][y]) {
                        count+=1
                    } else {
                        count+=1
                        break
                    }
                }
                mvis[i][j][t] = count
                t = 3
                count = 0
                for(y in j+1 until jmax) {
                    if (m[i][j] > m[i][y]) {
                        count+=1
                    } else {
                        count+=1
                        break
                    }
                }
                mvis[i][j][t] = count
                var current = mvis[i][j][0] * mvis[i][j][1] *mvis[i][j][2] *mvis[i][j][3]
                if (current > max) {
                    max = current
                }
            }
        }

        return max
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
