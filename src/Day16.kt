import java.util.*

fun main() {
    class Valve(var name: String, var rate: Int, var next: MutableMap<String, Int>) {

    }
    fun part1(input: List<String>): Int {
        var map = mutableMapOf<String, Valve>()
        for(l in input) {
            //Valve TN has flow rate=0; tunnels lead to valves IX, ZZ
            val match = Regex("Valve ([A-Z]+) has flow rate=(\\d+); (tunnel leads to valve|tunnels lead to valves) (.*)").find(l)!!
            val (valve, ratestr, dummy, nextstr) = match.destructured
            var rate = ratestr.toInt()
            var next_list = nextstr.split(", ")
            var next = mutableMapOf<String, Int>()
            for(n in next_list) {
                next[n] = 1
            }
            map[valve] = Valve(valve, rate, next)
        }
        var toDelete = mutableSetOf<String>()
        for(v in map) {
            if (v.value.rate == 0 && v.key != "AA") {
                toDelete.add(v.key)
                for(n in v.value.next) {
                    for(m in v.value.next) {
                        if (m.key > n.key) {
                            var direct_distance = n.value + m.value
                            var n_valve = map[n.key]!!
                            var m_valve = map[m.key]!!
                            n_valve.next.remove(v.key)
                            m_valve.next.remove(v.key)
                            if (n_valve.next.containsKey(m.key)) {
                                n_valve.next[m.key] = direct_distance.coerceAtMost(n_valve.next[m.key]!!)
                            } else {
                                n_valve.next[m.key] = direct_distance
                            }
                            if (m_valve.next.containsKey(n.key)) {
                                m_valve.next[n.key] = direct_distance.coerceAtMost(m_valve.next[n.key]!!)
                            } else {
                                m_valve.next[n.key] = direct_distance
                            }
                        }
                    }
                }
            }
        }
        for(k in toDelete) {
            map.remove(k)
        }
        var distances = mutableMapOf<String, MutableMap<String, Int>>()
        for(v in map) {
            var dist = mutableMapOf<String, Int>()
            dist[v.key] = 0
            var visited = mutableSetOf<String>()
            visited.add(v.key)
            var q = LinkedList<String>()
            q.add(v.key)
            while(!q.isEmpty()) {
                var current = q.poll()
                var d = dist[current]!!
                for(valve in map[current]!!.next) {
                    if(!visited.contains(valve.key)) {
                        visited.add(valve.key)
                        dist[valve.key] = d + valve.value
                        q.add(valve.key)
                    }
                }
            }
            distances[v.key] = dist
        }
        var valveMasks = mutableMapOf<String, Int>()
        var maskValves = mutableMapOf<Int, String>()
        for(k in map.keys) {
            val mask = 1 shl valveMasks.size
            valveMasks[k] = mask
            maskValves[mask] = k
        }
        fun flow(mask: Int): Int {
            var res = 0
            for(v in valveMasks) {
                if (v.value and mask != 0) {
                    res+=map[v.key]!!.rate
                }
            }
            return res
        }
        var start = "AA"
        var time = 30
        var dp = Array(31) {Array(map.size) { Array(1 shl map.size) { -100000000 } } }
        var prev = Array(31) {Array(map.size) { Array(1 shl map.size) { Triple(0,0,0) } } }
        for(i in 0 until map.size) {
            var mask = 1 shl i
            var valveName = maskValves[mask]!!
            val dist = distances["AA"]!![valveName]!!
            dp[dist + 1][i][1 shl i] = 0
        }

        var volume = 0
        for(i in 1..time) {
            for(j in 0 until (1 shl map.size)) {
                for(k in 0 until map.size) {
                    var f = flow(j)
                    var hold = dp[i-1][k][j] + f
                    if (hold > dp[i][k][j]) {
                        dp[i][k][j] = hold
                        prev[i][k][j] = Triple(i-1, k, j)
                    }

                    volume = Math.max(volume, dp[i][k][j])

                    val maskK = 1 shl k
                    if (((maskK) and j) == 0) {
                        continue
                    }

                    for(l in 0 until map.size) {
                        var maskL = 1 shl l
                        if (((maskL) and j) != 0) {
                            continue
                        }

                        val d = distances[maskValves[maskK]!!]!![maskValves[maskL]!!]!!
                        if (i + d + 1 > time) {
                            continue
                        }

                        val value = dp[i][k][j] + f * (d + 1);
                        if (value > dp[i + d + 1][l][j or (maskL)]) {
                            dp[i + d + 1][l][j or (maskL)] = value;
                            prev[i + d + 1][l][j or (maskL)] = Triple(i, k, j)
                        }
                    }
                }
            }
        }

        return volume
    }




    fun part2(input: List<String>): Int {
        var map = mutableMapOf<String, Valve>()
        for(l in input) {
            //Valve TN has flow rate=0; tunnels lead to valves IX, ZZ
            val match = Regex("Valve ([A-Z]+) has flow rate=(\\d+); (tunnel leads to valve|tunnels lead to valves) (.*)").find(l)!!
            val (valve, ratestr, dummy, nextstr) = match.destructured
            var rate = ratestr.toInt()
            var next_list = nextstr.split(", ")
            var next = mutableMapOf<String, Int>()
            for(n in next_list) {
                next[n] = 1
            }
            map[valve] = Valve(valve, rate, next)
        }
        var toDelete = mutableSetOf<String>()
        for(v in map) {
            if (v.value.rate == 0 && v.key != "AA") {
                toDelete.add(v.key)
                for(n in v.value.next) {
                    for(m in v.value.next) {
                        if (m.key > n.key) {
                            var direct_distance = n.value + m.value
                            var n_valve = map[n.key]!!
                            var m_valve = map[m.key]!!
                            n_valve.next.remove(v.key)
                            m_valve.next.remove(v.key)
                            if (n_valve.next.containsKey(m.key)) {
                                n_valve.next[m.key] = direct_distance.coerceAtMost(n_valve.next[m.key]!!)
                            } else {
                                n_valve.next[m.key] = direct_distance
                            }
                            if (m_valve.next.containsKey(n.key)) {
                                m_valve.next[n.key] = direct_distance.coerceAtMost(m_valve.next[n.key]!!)
                            } else {
                                m_valve.next[n.key] = direct_distance
                            }
                        }
                    }
                }
            }
        }
        for(k in toDelete) {
            map.remove(k)
        }
        var distances = mutableMapOf<String, MutableMap<String, Int>>()
        for(v in map) {
            var dist = mutableMapOf<String, Int>()
            dist[v.key] = 0
            var visited = mutableSetOf<String>()
            visited.add(v.key)
            var q = LinkedList<String>()
            q.add(v.key)
            while(!q.isEmpty()) {
                var current = q.poll()
                var d = dist[current]!!
                for(valve in map[current]!!.next) {
                    if(!visited.contains(valve.key)) {
                        visited.add(valve.key)
                        dist[valve.key] = d + valve.value
                        q.add(valve.key)
                    }
                }
            }
            distances[v.key] = dist
        }
        var valveMasks = mutableMapOf<String, Int>()
        var maskValves = mutableMapOf<Int, String>()
        for(k in map.keys) {
            val mask = 1 shl valveMasks.size
            valveMasks[k] = mask
            maskValves[mask] = k
        }
        fun flow(mask: Int): Int {
            var res = 0
            for(v in valveMasks) {
                if (v.value and mask != 0) {
                    res+=map[v.key]!!.rate
                }
            }
            return res
        }
        var start = "AA"
        var time = 26
        var dp = Array(31) {Array(map.size) { Array(1 shl map.size) { -100000000 } } }
        var prev = Array(31) {Array(map.size) { Array(1 shl map.size) { Triple(0,0,0) } } }
        for(i in 0 until map.size) {
            var mask = 1 shl i
            var valveName = maskValves[mask]!!
            val dist = distances["AA"]!![valveName]!!
            dp[dist + 1][i][1 shl i] = 0
        }

        var volume = 0
        for(i in 1..time) {
            for(j in 0 until (1 shl map.size)) {
                for(k in 0 until map.size) {
                    var f = flow(j)
                    var hold = dp[i-1][k][j] + f
                    if (hold > dp[i][k][j]) {
                        dp[i][k][j] = hold
                        prev[i][k][j] = Triple(i-1, k, j)
                    }

                    volume = Math.max(volume, dp[i][k][j])

                    val maskK = 1 shl k
                    if (((maskK) and j) == 0) {
                        continue
                    }

                    for(l in 0 until map.size) {
                        var maskL = 1 shl l
                        if (((maskL) and j) != 0) {
                            continue
                        }

                        val d = distances[maskValves[maskK]!!]!![maskValves[maskL]!!]!!
                        if (i + d + 1 > time) {
                            continue
                        }

                        val value = dp[i][k][j] + f * (d + 1);
                        if (value > dp[i + d + 1][l][j or (maskL)]) {
                            dp[i + d + 1][l][j or (maskL)] = value;
                            prev[i + d + 1][l][j or (maskL)] = Triple(i, k, j)
                        }
                    }
                }
            }
        }
        volume = 0
        val maxMask = 1 shl map.size
        for(i in 0 until maxMask) {
            for(j in 0 until maxMask) {
                if ((i and j) != j) {
                    continue
                }

                var a = -99999999;
                var b = -99999999;

                for (k in 0 until map.size) {
                    a = Math.max(a, dp[26][k][j])
                }

                for (k in 0 until map.size) {
                    b = Math.max(b, dp[26][k][i and j.inv()])
                }

                volume = Math.max(volume, a + b)
            }
        }

        return volume
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 1651)
    check(part2(testInput) == 1707)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
