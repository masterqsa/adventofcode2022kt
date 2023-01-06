class MatrixReference(var n: Int, var row: Int, var col: Int) {
}

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for(l in input) {
            var split = l.split(',')
            var first = split[0].split('-')
            var second = split[1].split('-')
            var a = first[0].toInt()
            var b = first[1].toInt()
            var x = second[0].toInt()
            var y = second[1].toInt()
            if ((a<=x && b>=y) || (a>=x && b<=y)) {
                result+=1
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {

        var result = 0
        for(l in input) {
            var split = l.split(',')
            var first = split[0].split('-')
            var second = split[1].split('-')
            var a = first[0].toInt()
            var b = first[1].toInt()
            var x = second[0].toInt()
            var y = second[1].toInt()
            if ((a<=x && b>=y) || (a>=x && b<=y) || ((a<=x) && (b>=x)) || ((a>=x) && (a<=y))) {
                result+=1
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
