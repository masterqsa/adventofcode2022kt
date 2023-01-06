import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min

fun main() {
    class Dir(var name: String, var root: Dir?) {
        var children = mutableMapOf<String, Dir>()
        var size = 0
        fun AddDir(newDir: Dir) {
            children[newDir.name] = newDir
        }
        fun AddFile(fileSize: Int) {
            size+=fileSize
        }
        fun CalcSize(): Int {
            size+=children.map{ it.value.CalcSize() }.sum()
            return size
        }
        fun FindDirs(sizeLimit: Int): Int {
            var result = 0
            if (size <= sizeLimit) {
                result = size
            }
            result+=children.map { it.value.FindDirs(sizeLimit)}.sum()
            return result
        }
        fun FindBest(req: Int, min: Int): Int {
            var current_min = min
            if (size >= req && size < min) {
                current_min = size
            }
            if (children.any()) {
                var best = children.map { it.value.FindBest(req, current_min) }.min()
                if (best < current_min) {
                    current_min = best
                }
            }
            return current_min
        }
    }
    fun part1(input: List<String>): Int {
        var root: Dir = Dir("/", null)
        var current: Dir = root

        for(l in input) {
            var split = l.split(' ')
            if (split[0] == "$") {
                if (split[1] == "cd") {
                    if (split[2] == "/") {
                        current = root
                    } else if (split[2] == "..") {
                        current = current.root!!
                    } else {
                        current = (current!!).children[split[2]]!!
                    }
                } else {
                    // ls - no action
                }
            } else if (split[0] == "dir"){
                var newDir = Dir(split[1], current)
                current.AddDir(newDir)
            } else {
                current.AddFile(split[0].toInt())
            }
        }

        root.CalcSize()


        return root.FindDirs(100000)
    }



    fun part2(input: List<String>): Int {
        var disk_size = 70000000
        var required_free = 30000000
        var root: Dir = Dir("/", null)
        var current: Dir = root

        for(l in input) {
            var split = l.split(' ')
            if (split[0] == "$") {
                if (split[1] == "cd") {
                    if (split[2] == "/") {
                        current = root
                    } else if (split[2] == "..") {
                        current = current.root!!
                    } else {
                        current = (current!!).children[split[2]]!!
                    }
                } else {
                    // ls - no action
                }
            } else if (split[0] == "dir"){
                var newDir = Dir(split[1], current)
                current.AddDir(newDir)
            } else {
                current.AddFile(split[0].toInt())
            }
        }

        root.CalcSize()

        //23595140
        var res = root.FindBest(root.size - (disk_size-required_free), root.size)
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
