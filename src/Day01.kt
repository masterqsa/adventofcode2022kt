fun main() {
    fun part1(input: List<String>): Int {
        var max = 0;
        for(i in input) {
            var current = i.split("\n", "\r\n").map { it.toInt() }.sum()
            if (current > max) {
                max = current;
            }
        }

        return max
    }

    fun part2(input: List<String>): Int {
        var s = input.map { it -> it.split("\n", "\r\n").sumOf { it.toInt() } }.sortedDescending().take(3).sum()
        return s
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputSplitByEmptyLines("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInputSplitByEmptyLines("Day01")
    check(part2(testInput) == 45000)
    println(part1(input))
    println(part2(input))
}
