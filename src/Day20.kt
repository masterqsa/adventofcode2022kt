import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    data class State(var step: Int, var r_ore: Int, var r_clay: Int, var r_obs: Int, var r_geo: Int,
        var ore: Int = 0,
        var clay: Int = 0,
        var obs: Int = 0,
        var geo: Int = 0,
        var dr_geo: Int = 0,
        var dr_obs: Int = 0,
        var dr_clay: Int = 0,
        var dr_ore: Int = 0)

    fun part1(input: List<String>): Int {
        var result = 0
        for(l in input) {
            //Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 2 ore and 7 obsidian.
            val match = Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.").find(l)!!
            val (id, oreore_s, clayore_s, obsore_s, obsclay_s, geoore_s, geoobs_s) = match.destructured
            val oreore = oreore_s.toInt()
            val clayore = clayore_s.toInt()
            val obsore = obsore_s.toInt()
            val obsclay = obsclay_s.toInt()
            val geoore = geoore_s.toInt()
            val geoobs = geoobs_s.toInt()

            var state = State(0, 1, 0 ,0,0)
            var seen = mutableSetOf<State>()
            fun Step(state: State): Int {
                if (state.step == 24) {
                    return state.geo
                }
                var actions = mutableListOf<State>()
                if (state.obs >= geoobs && state.ore>=geoore && state.step < 23) {
                    var action = state.copy()
                    action.dr_geo=1
                    actions.add(action)
                }
                if (state.clay>=obsclay && state.ore>=obsore && state.step < 22) {
                    var action = state.copy()
                    action.dr_obs=1
                    actions.add(action)
                }
                if (state.ore>=clayore && state.step < 18 ) {
                    var action = state.copy()
                    action.dr_clay=1
                    actions.add(action)
                }
                if (state.ore>=oreore && state.step < 10) {
                    var action = state.copy()
                    action.dr_ore=1
                    actions.add(action)
                }
                if (actions.size < 4) {
                    actions.add(state.copy())
                }
                for(a in actions) {
                    a.geo += a.r_geo
                    a.obs += a.r_obs
                    a.clay += a.r_clay
                    a.ore += a.r_ore
                }
                for(a in actions) {
                    if (a.dr_geo == 1) {
                        a.obs-=geoobs
                        a.ore-=geoore
                        a.r_geo+=1
                        a.dr_geo = 0
                    }
                    if (a.dr_obs == 1) {
                        a.clay-=obsclay
                        a.ore-=obsore
                        a.r_obs+=1
                        a.dr_obs = 0
                    }
                    if (a.dr_clay == 1) {
                        a.ore-=clayore
                        a.r_clay+=1
                        a.dr_clay = 0
                    }
                    if (a.dr_ore == 1) {
                        a.ore-=oreore
                        a.r_ore+=1
                        a.dr_ore = 0
                    }
                }
                var best = 0
                for(a in actions) {
                    a.step+=1
                    if (!seen.contains(a)) {
                        seen.add(a)
                        var current = Step(a)
                        if (current > best) {
                            best = current
                        }
                    }
                }
                return best
            }

            var geodes = Step(state)
            result+=(id.toInt()* geodes)
            println(id + ", geodes = " + geodes.toString())
        }
        return result
    }



    fun part2(input: List<String>): Int {
        var result = 1
        for(i in 0..Math.min(input.size-1, 2)) {
            var l = input[i]

            //Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 11 clay. Each geode robot costs 2 ore and 7 obsidian.
            val match = Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.").find(l)!!
            val (id, oreore_s, clayore_s, obsore_s, obsclay_s, geoore_s, geoobs_s) = match.destructured
            val oreore = oreore_s.toInt()
            val clayore = clayore_s.toInt()
            val obsore = obsore_s.toInt()
            val obsclay = obsclay_s.toInt()
            val geoore = geoore_s.toInt()
            val geoobs = geoobs_s.toInt()

            var state = State(0, 1, 0 ,0,0)
            var seen = mutableSetOf<State>()
            fun Step(state: State): Int {
                if (state.step == 32) {
                    return state.geo
                }
                var actions = mutableListOf<State>()
                if (state.obs >= geoobs && state.ore>=geoore && state.step < 31) {
                    var action = state.copy()
                    action.dr_geo=1
                    actions.add(action)
                }
                if (state.clay>=obsclay && state.ore>=obsore && state.step < 30) {
                    var action = state.copy()
                    action.dr_obs=1
                    actions.add(action)
                }
                if (state.ore>=clayore && state.step < 21 ) {
                    var action = state.copy()
                    action.dr_clay=1
                    actions.add(action)
                }
                if (state.ore>=oreore && state.step < 12) {
                    var action = state.copy()
                    action.dr_ore=1
                    actions.add(action)
                }
                if (actions.size < 4) {
                    actions.add(state.copy())
                }
                for(a in actions) {
                    a.geo += a.r_geo
                    a.obs += a.r_obs
                    a.clay += a.r_clay
                    a.ore += a.r_ore
                }
                for(a in actions) {
                    if (a.dr_geo == 1) {
                        a.obs-=geoobs
                        a.ore-=geoore
                        a.r_geo+=1
                        a.dr_geo = 0
                    }
                    if (a.dr_obs == 1) {
                        a.clay-=obsclay
                        a.ore-=obsore
                        a.r_obs+=1
                        a.dr_obs = 0
                    }
                    if (a.dr_clay == 1) {
                        a.ore-=clayore
                        a.r_clay+=1
                        a.dr_clay = 0
                    }
                    if (a.dr_ore == 1) {
                        a.ore-=oreore
                        a.r_ore+=1
                        a.dr_ore = 0
                    }
                }
                var best = 0
                if (state.step>30 && actions.size > 1) {
                    actions.sortByDescending { it.geo*3 + it.r_geo*2 + it.r_obs }
                    actions = mutableListOf<State>(actions[0], actions[1])
                }
                for(a in actions) {
                    a.step+=1

                    if (!seen.contains(a)) {
                        seen.add(a)
                        var current = Step(a)
                        if (current > best) {
                            best = current
                        }
                    }
                }
                return best
            }

            var geodes = Step(state)
            result*=geodes
            println(id + ", geodes = " + geodes.toString())
        }
        // 6825 too low, 7980, 10336
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    //check(part1(testInput) == 33)
    //check(part2(testInput) == 3472)

    val input = readInput("Day20")
    //println(part1(input))
    println(part2(input))
}
