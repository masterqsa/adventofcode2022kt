import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

enum class Rotation {
    XYZ, YZX, ZXY, XZY, YXZ, ZYX
}
class Sensor(var id: Int, var beacons: MutableList<Beacon>) {
    var rotation: Rotation? = null
    var dx: Int? = null
    var dy: Int? = null
    var dz: Int? = null
    var offset: Offset? = null
    fun isRotationSet(): Boolean {
        return rotation != null
    }
    fun setRotation(r: Rotation, _dx: Int, _dy: Int, _dz: Int) {
        rotation = r
        dx = _dx
        dy = _dy
        dz = _dz
    }
    fun rotate(r: Rotation, dx: Int, dy: Int, dz: Int): MutableList<Beacon> {
        return (beacons.map { it.rotate(r, dx, dy, dz) }).toMutableList()
    }
    fun distance(s: Sensor): Int {
        return abs(offset!!.dx - s.offset!!.dx) +
                abs(offset!!.dy - s.offset!!.dy)+
                abs(offset!!.dz - s.offset!!.dz)
    }
}
class Beacon(var x: Int, var y: Int, var z: Int, var id: Int) {
    fun rotate(r: Rotation, dx: Int, dy: Int, dz: Int): Beacon {
        var nx = 0
        var ny = 0
        var nz = 0
        when (r) {
            Rotation.XYZ -> {
                nx = x
                ny = y
                nz = z
            }
            Rotation.ZXY -> {
                nx = z
                ny = x
                nz = y
            }
            Rotation.YZX -> {
                nx = y
                ny = z
                nz = x
            }
            Rotation.XZY -> {
                nx = x
                ny = z
                nz = y
            }
            Rotation.YXZ -> {
                nx = y
                ny = x
                nz = z
            }
            Rotation.ZYX -> {
                nx = z
                ny = y
                nz = x
            }
        }
        nx*=dx
        ny*=dy
        nz*=dz
        return Beacon(nx, ny, nz, id)
    }
    fun subtract(b: Beacon): Offset {
        return Offset(x-b.x, y - b.y, z-b.z, b.id)
    }
    fun applyOffset(o: Offset): Offset {
        return Offset(x+o.dx, y + o.dy, z+o.dz, id)
    }
}
class Offset(var dx: Int, var dy: Int, var dz: Int, var id: Int?) {
    fun isEqual(b: Offset): Boolean {
        return dx == b.dx && dy == b.dy && dz == b.dz
    }
    fun add(b: Offset): Offset {
        return Offset(dx + b.dx, dy + b.dy, dz + b.dz, null)
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        var sensors: MutableList<Sensor> = mutableListOf<Sensor>()
        var current: Sensor = Sensor(0, mutableListOf<Beacon>())
        var beaconsCount = 0
        for(i in input.indices) {
            if(input[i] == "") {
                sensors.add(current)
                continue
            } else if (input[i].startsWith("---")) {
                var id = input[i].replace("--- scanner ", "").substringBefore(' ').toInt()
                current = Sensor(id, mutableListOf<Beacon>())
                continue
            }
            var coords = input[i].split(',').map(String::toInt)
            current.beacons.add(Beacon(coords[0],coords[1],coords[2], beaconsCount++))
        }
        sensors[0].offset = Offset(0,0,0,null)
        sensors[0].setRotation(Rotation.XYZ, 1, 1, 1)

        var identified = mutableSetOf<Int>()
        identified.add(0)
        var unidentified = mutableSetOf<Int>()
        for(i in 0 until sensors.size) {
            unidentified.add(i)
        }
        unidentified.remove(0)
        while(unidentified.isNotEmpty() && identified.isNotEmpty()) {
            var newIdentified = mutableSetOf<Int>()
            for(from in identified) {
                for(to in unidentified) {
                    var found = false
                    for(r in Rotation.values()) {
                        for(dx in setOf(-1, 1)) {
                            for(dy in setOf(-1, 1)) {
                                for(dz in setOf(-1, 1)) {
                                    var rotatedTo = sensors[to].rotate(r, dx, dy, dz)
                                    var base = 0
                                    while(!found && base < sensors[from].beacons.size) {
                                        var offsets = mutableListOf<Offset>()
                                        for(b in sensors[from].beacons.indices) {
                                            if (b == base) continue
                                            var newOffset = sensors[from].beacons[base].subtract(sensors[from].beacons[b])
                                            newOffset.id = b
                                            offsets.add(newOffset)
                                        }
                                        for(ta in rotatedTo.indices) {
                                            var offsetsTo = mutableListOf<Offset>()
                                            for(tb in rotatedTo.indices) {
                                                if (ta == tb) continue
                                                var newOffset = rotatedTo[ta].subtract(rotatedTo[tb])
                                                newOffset.id = tb
                                                offsetsTo.add(newOffset)
                                            }
                                            var count = 1 // base scanners are aligned
                                            for(of in offsets) {
                                                for(ot in offsetsTo) {
                                                    if (of.isEqual(ot)) {
                                                        count++
                                                    }
                                                }
                                            }
                                            if (count>=12) {
                                                newIdentified.add(to)
                                                sensors[to].setRotation(r, dx, dy, dz)
                                                sensors[to].beacons = rotatedTo
                                                var minId = min(sensors[to].beacons[ta].id, sensors[from].beacons[base].id)
                                                sensors[to].beacons[ta].id = minId
                                                sensors[from].beacons[base].id = minId
                                                for(of in offsets) {
                                                    for(ot in offsetsTo) {
                                                        if (of.isEqual(ot)) {
                                                            minId = min(sensors[to].beacons[ot.id!!].id, sensors[from].beacons[of.id!!].id)
                                                            sensors[to].beacons[ot.id!!].id = minId
                                                            sensors[from].beacons[of.id!!].id = minId
                                                        }
                                                    }
                                                }
                                                sensors[to].offset = sensors[from].offset?.add(sensors[from].beacons[base].subtract(sensors[to].beacons[ta]))
                                                found = true
                                                break
                                            }
                                        }
                                        base++
                                    }
                                    if (found) break
                                }
                                if (found) break
                            }
                            if (found) break
                        }
                        if (found) break
                    }
                }
            }
            identified.clear()
            identified.addAll(newIdentified)
            unidentified.removeAll(newIdentified)
        }

        var beaconCoordSet = mutableSetOf<String>()
        for(s in sensors) {
            for(b in s.beacons) {
                var o = b.applyOffset(s.offset!!)
                beaconCoordSet.add(o.dx.toString()+","+o.dy.toString()+","+o.dz.toString())
            }
        }
        println("unidentified sensors: " + unidentified.count())
        println("coords set count:" + beaconCoordSet.count())
        return beaconCoordSet.count()
    }



    fun part2(input: List<String>): Int {
        var sensors: MutableList<Sensor> = mutableListOf<Sensor>()
        var current: Sensor = Sensor(0, mutableListOf<Beacon>())
        var beaconsCount = 0
        for(i in input.indices) {
            if(input[i] == "") {
                sensors.add(current)
                continue
            } else if (input[i].startsWith("---")) {
                var id = input[i].replace("--- scanner ", "").substringBefore(' ').toInt()
                current = Sensor(id, mutableListOf<Beacon>())
                continue
            }
            var coords = input[i].split(',').map(String::toInt)
            current.beacons.add(Beacon(coords[0],coords[1],coords[2], beaconsCount++))
        }
        sensors[0].offset = Offset(0,0,0,null)
        sensors[0].setRotation(Rotation.XYZ, 1, 1, 1)

        var identified = mutableSetOf<Int>()
        identified.add(0)
        var unidentified = mutableSetOf<Int>()
        for(i in 0 until sensors.size) {
            unidentified.add(i)
        }
        unidentified.remove(0)
        while(unidentified.isNotEmpty() && identified.isNotEmpty()) {
            var newIdentified = mutableSetOf<Int>()
            for(from in identified) {
                for(to in unidentified) {
                    var found = false
                    for(r in Rotation.values()) {
                        for(dx in setOf(-1, 1)) {
                            for(dy in setOf(-1, 1)) {
                                for(dz in setOf(-1, 1)) {
                                    var rotatedTo = sensors[to].rotate(r, dx, dy, dz)
                                    var base = 0
                                    while(!found && base < sensors[from].beacons.size) {
                                        var offsets = mutableListOf<Offset>()
                                        for(b in sensors[from].beacons.indices) {
                                            if (b == base) continue
                                            var newOffset = sensors[from].beacons[base].subtract(sensors[from].beacons[b])
                                            newOffset.id = b
                                            offsets.add(newOffset)
                                        }
                                        for(ta in rotatedTo.indices) {
                                            var offsetsTo = mutableListOf<Offset>()
                                            for(tb in rotatedTo.indices) {
                                                if (ta == tb) continue
                                                var newOffset = rotatedTo[ta].subtract(rotatedTo[tb])
                                                newOffset.id = tb
                                                offsetsTo.add(newOffset)
                                            }
                                            var count = 1 // base scanners are aligned
                                            for(of in offsets) {
                                                for(ot in offsetsTo) {
                                                    if (of.isEqual(ot)) {
                                                        count++
                                                    }
                                                }
                                            }
                                            if (count>=12) {
                                                newIdentified.add(to)
                                                sensors[to].setRotation(r, dx, dy, dz)
                                                sensors[to].beacons = rotatedTo
                                                var minId = min(sensors[to].beacons[ta].id, sensors[from].beacons[base].id)
                                                sensors[to].beacons[ta].id = minId
                                                sensors[from].beacons[base].id = minId
                                                for(of in offsets) {
                                                    for(ot in offsetsTo) {
                                                        if (of.isEqual(ot)) {
                                                            minId = min(sensors[to].beacons[ot.id!!].id, sensors[from].beacons[of.id!!].id)
                                                            sensors[to].beacons[ot.id!!].id = minId
                                                            sensors[from].beacons[of.id!!].id = minId
                                                        }
                                                    }
                                                }
                                                sensors[to].offset = sensors[from].offset?.add(sensors[from].beacons[base].subtract(sensors[to].beacons[ta]))
                                                found = true
                                                break
                                            }
                                        }
                                        base++
                                    }
                                    if (found) break
                                }
                                if (found) break
                            }
                            if (found) break
                        }
                        if (found) break
                    }
                }
            }
            identified.clear()
            identified.addAll(newIdentified)
            unidentified.removeAll(newIdentified)
        }

        var maxDistance = 0
        for(i in sensors.indices) {
            for(j in sensors.indices) {
                var d = sensors[i].distance(sensors[j])
                if (d > maxDistance)
                    maxDistance = d
            }
        }

        return maxDistance
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 79)
    check(part2(testInput) == 3621)

    val input = readInput("Day19")
    println(part1(input))
    println(part2(input))
}
