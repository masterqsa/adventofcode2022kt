fun main() {
    fun getScore(x: String, y: String): Int {
        var cost = mapOf<String, Int>("A" to 1,"B" to 2,"C" to 3)
        var res = 0;
        if (x == y) {
            res = 3
        } else if ((x == "A" && y == "B") || (x == "B" && y == "C") || (x == "C" && y == "A")) {
            res = 6
        }
        return cost[y]!! + res
    }
    fun part1(input: List<String>): Int {

        var mapping = mapOf<String,String>("X" to "A", "Y" to "B", "Z" to "C")
        var score = 0;
        for(i in input) {
            var split = i.split(' ')
            var x = split[0]
            var y = split[1]
            score+=getScore(x, mapping[y]!!)
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var win = mapOf<String,String>("A" to "B", "B" to "C", "C" to "A")
        var lose = mapOf<String,String>("A" to "C", "B" to "A", "C" to "B")
        var score = 0;
        for(i in input) {
            var split = i.split(' ')
            var x = split[0]
            var y = x
            var outcome = split[1]
            if (outcome == "X") {
                y = lose[x]!!
            } else if (outcome == "Z") {
                y = win[x]!!
            }
            score+=getScore(x, y)
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
