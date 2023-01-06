import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    class Shape(var locs: List<Pair<Int,Int>>, var height: Int, var width: Int) {}
    fun part1(input: List<String>): Int {

        var glass = mutableSetOf<Pair<Int, Int>>()
        var width = 7
        var height = -1
        fun checkCollision(x: Int, y: Int, dx: Int, dy:Int, shape: Shape): Boolean {
            if (dx == -1 && x == 0) return true
            if (dx == 1 && x+shape.width == width) return true
            if (dy == -1 && y == 0) return true
            for(p in shape.locs) {
                if (glass.contains(Pair(x+p.first+dx, y+p.second+dy))) {
                    return true
                }
            }
            return false
        }
        var shapes = mutableListOf<Shape>()
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0)),1, 4))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(1,0), Pair(0,1), Pair(1,1), Pair(2,1), Pair(1,2)),3,3))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(2,1), Pair(2,2)),3, 3))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(0,1), Pair(0,2), Pair(0,3)),4, 1))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1)),2, 2))
        var winds = input[0]
        var wind = 0
        val windLength = winds.length
        for(i in 0 until 2022) {
            var shape = shapes[i % 5]
            var x = 2
            var y = height + 4
            var stop = false
            while(!stop) {
                val dir = if (winds[wind] == '<') -1 else 1
                wind = (wind + 1) % windLength
                if (!checkCollision(x, y, dir, 0, shape)) {
                    x += dir
                }
                if (!checkCollision(x, y, 0, -1, shape)) {
                    y -= 1
                } else {
                    stop = true
                }
            }
            for(p in shape.locs) {
                glass.add(Pair(p.first + x, p.second + y))
            }
            height = Math.max(height, y + shape.height-1)
            /*println("-------")
            for(y in 0 .. height) {
                var s = ""
                for(x in 0 .. 6){
                    if (glass.contains(Pair(x,y))){
                        s+='#'
                    } else {
                        s+='.'
                    }
                }
                println('|'+s+'|')
            }*/
        }

        return height+1
    }



    fun part2(input: List<String>): Long {
        val steps = 1000000000000
        var glass = mutableSetOf<Pair<Int, Int>>()
        var width = 7
        var height = -1
        fun checkCollision(x: Int, y: Int, dx: Int, dy:Int, shape: Shape): Boolean {
            if (dx == -1 && x == 0) return true
            if (dx == 1 && x+shape.width == width) return true
            if (dy == -1 && y == 0) return true
            for(p in shape.locs) {
                if (glass.contains(Pair(x+p.first+dx, y+p.second+dy))) {
                    return true
                }
            }
            return false
        }
        var shapes = mutableListOf<Shape>()
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(3,0)),1, 4))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(1,0), Pair(0,1), Pair(1,1), Pair(2,1), Pair(1,2)),3,3))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(2,0), Pair(2,1), Pair(2,2)),3, 3))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(0,1), Pair(0,2), Pair(0,3)),4, 1))
        shapes.add(Shape(mutableListOf<Pair<Int, Int>>(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1)),2, 2))
        var winds = input[0]
        var wind = 0
        val windLength = winds.length
        var prevHeight = 0
        var oldh = 0
        var prevStep = 0
        var h1447 = 0
        var reset = false
        for(i in 0 until windLength*5) {
            var shape = shapes[i % 5]
            var x = 2
            var y = height + 4
            var stop = false
            while(!stop) {
                val dir = if (winds[wind] == '<') -1 else 1

                if (!checkCollision(x, y, dir, 0, shape)) {
                    x += dir
                }
                if (!checkCollision(x, y, 0, -1, shape)) {
                    y -= 1
                } else {
                    stop = true
                }

                wind += 1
                if (wind == windLength) {
                    println("step="+i.toString()+", d step="+(i-prevStep).toString() +" ,d height="+(height-prevHeight).toString()+", height="+height.toString())
                    prevHeight = height
                    prevStep = i
                    wind = 0
                    reset = true
                }
            }
            for(p in shape.locs) {
                glass.add(Pair(p.first + x, p.second + y))
            }
            height = Math.max(height, y + shape.height-1)
            if (reset) {
                println("i="+i.toString()+", h=" + height.toString()+", dh="+(height-oldh).toString())
                oldh = height
                reset = false
            }
            if (prevStep+1447 == i) {
                println("Extra Step Height = " + (height-oldh).toString())
            }
        }
        /*println("---------")
        for(y in 0 .. height) {
            var s = ""
            for(x in 0 .. 6){
                if (glass.contains(Pair(x,y))){
                    s+='#'
                } else {
                    s+='.'
                }
            }
            println('|'+s+'|')
        }*/
        return height+1L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    //check(part1(testInput) == 3068)
    //check(part2(testInput) == 1514285714288L)
    part2(testInput)

    val input = readInput("Day17")
    part2(input)
    println(part1(input))
    //println(part2(input))
}
