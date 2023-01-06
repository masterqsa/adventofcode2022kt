fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for(s in input) {
            var first = s.substring(0, s.length/2)
            var map = first.toCharArray().toSet()
            var second = s.substring(s.length/2, s.length)
            for(c in second) {
                if (map.contains(c)) {
                    if (c >= 'a') {
                        result += (c - 'a' + 1)
                        break
                    } else {
                        result += (c - 'A' + 27)
                        break
                    }
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for(i in 0 until input.size/3) {
            var map = mutableMapOf<Char, Int>()
            for(j in 0..2){
                var set = input[i*3+j].toCharArray().toSet()
                for(c in set) {
                    if (map.containsKey(c)) {
                        map[c] = map[c]!! + 1
                        if (map[c]!! == 3) {
                            if (c >= 'a') {
                                result += (c - 'a' + 1)
                            } else {
                                result += (c - 'A' + 27)
                            }
                        }
                    } else {
                        map[c] = 1
                    }
                }
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
