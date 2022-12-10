import kotlin.math.abs

fun main() {
    data class Coordinates (var x: Int, var y: Int) {
        override fun toString(): String {
            return "$x $y"
        }
    }

    fun getDirection(or: Coordinates, dest: Coordinates): Coordinates? {
        val xDiff = dest.x - or.x
        val yDiff = dest.y - or.y
        return if (abs(xDiff) >= 2 || abs(yDiff) >= 2) {
            val x = if (xDiff >= 1) 1 else if (xDiff <= -1) -1 else 0
            val y = if (yDiff >= 1) 1 else if (yDiff <= -1) -1 else 0
            Coordinates(x, y)
        } else {
            null
        }
    }

    fun part1(input: List<String>): Int {
        val visited = HashSet<Coordinates>()
        val hPos = Coordinates(0, 0)
        val tPos = Coordinates(0, 0)

        for (l in input) {
            val s = l.split(' ')[1].toInt()
            for (i in 0 until s) {
                if (l[0] == 'U') {
                    hPos.y++
                }
                if (l[0] == 'R') {
                    hPos.x++
                }
                if (l[0] == 'D') {
                    hPos.y--
                }
                if (l[0] == 'L') {
                    hPos.x--
                }
                val dir = getDirection(tPos, hPos)
                if (dir != null) {
                    tPos.x += dir.x
                    tPos.y += dir.y
                }
                visited.add(tPos.copy())
            }
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val visited = HashSet<Coordinates>()
        val hPos = Coordinates(0, 0)
        val tPos = Array(9) { Coordinates(0, 0) }

        for (l in input) {
            val s = l.split(' ')[1].toInt()
            for (i in 0 until s) {
                if (l[0] == 'U') {
                    hPos.y++
                }
                if (l[0] == 'R') {
                    hPos.x++
                }
                if (l[0] == 'D') {
                    hPos.y--
                }
                if (l[0] == 'L') {
                    hPos.x--
                }
                var prev = hPos
                for (t in tPos) {
                    val dir = getDirection(t, prev)
                    if (dir != null) {
                        t.x += dir.x
                        t.y += dir.y
                    }
                    prev = t
                }
                visited.add(tPos[8].copy())
            }
        }
        return visited.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../9linput")
    check(part1(testInput) == 13)

    val input = readInput("../../9input")
    println(part1(input))

    check(part2(testInput) == 1)
    val testInput2 = readInput("../../92linput")
    check(part2(testInput2) == 36)
    println(part2(input))
}
