import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Point(var x: Int, var y: Int)

    lateinit var occupied: HashMap<Int, HashSet<Int>>
    // For visualization
    lateinit var sandList: HashMap<Int, HashSet<Int>>
    var sandTime = false
    var minX = 500
    var maxX = 500

    fun addPoint(p: Point) {
        if (occupied.contains(p.y)) {
            occupied[p.y]!!.add(p.x)
        } else {
            occupied[p.y] = hashSetOf(p.x)
        }
        if (sandTime) {
            if (sandList.contains(p.y)) {
                sandList[p.y]!!.add(p.x)
            } else {
                sandList[p.y] = hashSetOf(p.x)
            }
        }
        if (p.x < minX) minX = p.x
        if (p.x > maxX) maxX = p.x
    }

    fun addPath(path: String) {
        val points = path.split(" -> ").map {
            val coors = it.split(",")
            Point(coors[0].toInt(), coors[1].toInt())
        }
        var prev = points[0]
        for (i in 1 until points.size) {
            if (points[i].x - prev.x != 0) {
                val init = min(prev.x, points[i].x)
                val end = max(prev.x, points[i].x)
                val xs = (init .. end).map { it }.toCollection(HashSet())
                if (occupied.contains(prev.y)) {
                    occupied[prev.y]!!.addAll(xs)
                } else {
                    occupied[prev.y] = xs
                }
                if (init < minX) minX = init
                if (end > maxX) maxX = end
            } else {
                val init = min(prev.y, points[i].y)
                val end = max(prev.y, points[i].y)
                for (y in init .. end) {
                    addPoint(Point(prev.x, y))
                }
            }
            prev = points[i]
        }
    }

    fun printCave() {
        val bottom = if (!sandTime) occupied.keys.max() + 2 else  occupied.keys.max() + 1
        for (j in 0..bottom) {
//            for (i in 410..580) {
            for (i in minX..maxX) {
                if (sandTime && sandList[j]?.contains(i) == true) {
                    print('o')
                } else if (occupied[j]?.contains(i) == true || j == bottom) {
                    print('#')
                } else  if (i == 500 && j == 0) {
                    print('x')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        occupied = hashMapOf()
        sandList = hashMapOf()
        sandTime = false

        input.forEach{ addPath(it) }

        printCave()
        sandTime = true

        val bottom = occupied.keys.max()

        var overflow = false
        var sand = 0
        while(!overflow) {
            var height = 0
            var x = 500
            while (true) {
                if (height > bottom) {
                    overflow = true
                    break
                }
                if (occupied[height]?.contains(x) == true) {
                    if (occupied[height]?.contains(x-1) == true) {
                        if (occupied[height]?.contains(x + 1) == true) {
                            addPoint(Point(x, height-1))
                            sand++
                            break
                        } else {
                            x++
                        }
                    } else {
                        x--
                    }
                } else {
                    height++
                }
            }
        }
        println("======================")
        printCave()
        println(sand)
        return sand
    }

    fun part2(input: List<String>): Int {
        occupied = hashMapOf()
        sandList = hashMapOf()
        sandTime = false

        input.forEach{ addPath(it) }
        val bottom = occupied.keys.max() + 2


        printCave()
        sandTime = true

        var overflow = false
        var sand = 0
        while(!overflow) {
            var height = 0
            var x = 500
            while (true) {
                if (occupied[height]?.contains(x) == true || height == bottom) {
                    if (height == 0) {
                        overflow = true
                        break
                    }
                    if (height == bottom) {
                        addPoint(Point(x, height-1))
                        sand++
                        break
                    }
                    if (occupied[height]?.contains(x-1) == true) {
                        if (occupied[height]?.contains(x + 1) == true) {
                            addPoint(Point(x, height-1))
                            sand++
                            break
                        } else {
                            x++
                        }
                    } else {
                        x--
                    }
                } else {
                    height++
                }
            }
        }
        println("======================")
        printCave()
        println(sand)
        return sand
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../14linput")
    check(part1(testInput) == 24)

    val input = readInput("../../14input")
    println(part1(input))

    check(part2(testInput) == 93)
    println(part2(input))
}
