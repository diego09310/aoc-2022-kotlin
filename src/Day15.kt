import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Point(var x: Int, var y: Int)

    lateinit var occupied: HashSet<Int>
    lateinit var beaconsInTarget: HashSet<Int>
    var targetRow: Int

    fun part1(input: List<String>): Int {
        occupied = hashSetOf()
        beaconsInTarget = hashSetOf()
        targetRow = if (input.size > 15) 2000000 else 10

        for (l in input) {
            val parts = l.replace("""[:,]""".toRegex(), "").split(" ")
            val sp = Point(parts[2].split("=")[1].toInt(), parts[3].split("=")[1].toInt())
            val bp = Point(parts[8].split("=")[1].toInt(), parts[9].split("=")[1].toInt())

            if (bp.y == targetRow) {
                beaconsInTarget.add(bp.x)
            }

            val dist = abs(bp.x - sp.x) + abs(bp.y - sp.y)

            if (abs(targetRow - sp.y) <= dist) {
                val extra = dist - abs(targetRow - sp.y)
                val xs = (sp.x - extra .. sp.x + extra).map { it }.toCollection(HashSet())
                occupied.addAll(xs)
            }
        }
        val result = occupied - beaconsInTarget
        println(result.size)
        return return result.size
    }

    data class Rang(var x0: Int, var x1: Int)

    fun consolidate(rX: ArrayList<Rang>) {
        var consolidated = false
        var toRemove: HashSet<Rang> = hashSetOf()
        while (!consolidated) {
            consolidated = true
            var prev = rX[0]
            for (i in 1 until rX.size) {
                if (prev.x1 in rX[i].x0..rX[i].x1) {
                    prev.x1 = rX[i].x1
                    consolidated = false
                    toRemove.add(rX[i])
                    continue
                }
                if (rX[i].x0 in prev.x0..prev.x1 && rX[i].x1 in prev.x0..prev.x1) {
                    toRemove.add(rX[i])
                    continue
                }
                prev = rX[i]
            }
            rX.removeAll(toRemove)
            toRemove.clear()
        }
    }

    fun addPoints(rX: ArrayList<Rang>, r0: Int, r1: Int) {
        var check = false
        var contained = false
        for (s in rX) {
            if (s.x0 in r0 .. r1) {
                s.x0 = r0
                check = true
            }
            if (s.x1 in r0 .. r1) {
                s.x1 = r1
                check = true
            }
            if (check) {
                break
            }
            if (r0 in s.x0 .. s.x1 && r1 in s.x0..s.x1) {
                contained = true
                break
            }
        }
        if (check) {
            consolidate(rX)
        } else if (!contained) {
            rX.add(Rang(r0, r1))
            rX.sortBy { it.x0 }
        }
    }

    fun part2(input: List<String>): Long {
        val sensorDist: HashMap<Point, Int> = hashMapOf()

        val maxSize = if (input.size > 15) 4000000 else 20

        for (l in input) {
            val parts = l.replace("""[:,]""".toRegex(), "").split(" ")
            val sp = Point(parts[2].split("=")[1].toInt(), parts[3].split("=")[1].toInt())
            val bp = Point(parts[8].split("=")[1].toInt(), parts[9].split("=")[1].toInt())
            val dist = abs(bp.x - sp.x) + abs(bp.y - sp.y)
            sensorDist[sp] = dist
        }

        lateinit var b: Point
        row@ for (r in 0 .. maxSize) {
            val rX: ArrayList<Rang> = arrayListOf()
            for (sd in sensorDist.entries) {
                val extra = sd.value - abs(r - sd.key.y)
                if (extra >= 0) {
                    val x0 = max(0, sd.key.x - extra)
                    val xN = min(maxSize, sd.key.x + extra)

                    addPoints(rX, x0, xN)
                    if (rX.size == 1 && rX[0].x0 == 0 && rX[0].x1 == maxSize) {
                        continue@row
                    }
                }
            }
            b = Point(rX[0].x1 + 1, r)
            break
        }

        val result = b.x * 4000000L + b.y
        println("b: $b")
        println(result)
        return result
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../15linput")
    check(part1(testInput) == 26)

    val input = readInput("../../15input")
    println(part1(input))

    check(part2(testInput) == 56000011L)
    println(part2(input))
}
