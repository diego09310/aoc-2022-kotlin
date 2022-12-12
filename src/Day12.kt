fun main() {
    data class Coor(var x: Int, var y: Int, var steps: Int)

    fun isReachable(coor1: Coor, coor2: Coor, map: MutableList<MutableList<Char>>, stepMap: Array<Array<Int>>): Boolean {
        return ((map[coor2.x][coor2.y].code <= map[coor1.x][coor1.y].code + 1 && map[coor2.x][coor2.y] != 'E') || (map[coor1.x][coor1.y].code >= 'y'.code && map[coor2.x][coor2.y] == 'E'))
    }

    fun examineCoor(toVisit: ArrayDeque<Coor>, map: MutableList<MutableList<Char>>, stepMap: Array<Array<Int>>): Int {
        val coor = toVisit.removeFirst()
        if (stepMap[coor.x][coor.y] < coor.steps) {
            return -1
        }
        if (map[coor.x][coor.y] == 'E') {
            return coor.steps
        }
        stepMap[coor.x][coor.y] = coor.steps

        for (i in -1..1) {
            for (j in -1..1) {
                if ((i == 0 || j == 0) && !(i == 0 && j == 0)) {
                    val nCoor = Coor(coor.x + i, coor.y + j, coor.steps + 1)
                    if (coor.x + i >= 0 && coor.x + i < map.size
                            && coor.y + j >= 0 && coor.y + j < map[0].size
                            && isReachable(coor, nCoor, map, stepMap)
                            && !toVisit.contains(nCoor)) {
                        toVisit.add(nCoor)
                    }
                }
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val map: MutableList<MutableList<Char>> = mutableListOf()
        input.forEach{ line -> map.add(line.toCharArray().map{it} as MutableList<Char>) }

        val toVisit = ArrayDeque<Coor>()
        val stepMap = Array(map.size) { Array(map[0].size) { Int.MAX_VALUE} }

        val start = Coor(-1, -1, 0)
        start@ for (i in 0 until map.size) {
            for (j in 0 until map[0].size) {
                if (map[i][j] == 'S') {
                    start.x = i
                    start.y = j
                    map[i][j] = 'a'
                    break@start
                }
            }
        }
        toVisit.add(start)

        while(toVisit.isNotEmpty()) {
//            println("===========")
//            toVisit.forEach{println(it)}
//            for (i in stepMap) {
//                i.forEach { print(it.toString().padStart(3, ' ')) }
//                println()
//            }
            val steps = examineCoor(toVisit, map, stepMap)
            if (steps != -1) {
                return steps
            }
        }

        return 0
    }

    fun examineCoor2(toVisit: ArrayDeque<Coor>, map: MutableList<MutableList<Char>>, stepMap: Array<Array<Int>>): Int {
        val coor = toVisit.removeFirst()

        if (stepMap[coor.x][coor.y] < coor.steps) {
            return -1
        }
        if (map[coor.x][coor.y] == 'a') {
            coor.steps = 0
        }
        if (map[coor.x][coor.y] == 'E') {
            for (i in 0 until coor.steps) {
                if (!stepMap.any{it.contains(i)}) {
                    return -1
                }
            }
            return coor.steps
        }

        toVisit.filter{!(it.x == coor.x && it.y == coor.y && it.steps > coor.steps)}

        stepMap[coor.x][coor.y] = coor.steps

        for (i in -1..1) {
            for (j in -1..1) {
                if ((i == 0 || j == 0) && !(i == 0 && j == 0)) {
                    val nCoor = Coor(coor.x + i, coor.y + j, coor.steps + 1)
                    if (coor.x + i >= 0 && coor.x + i < map.size
                            && coor.y + j >= 0 && coor.y + j < map[0].size
                            && isReachable(coor, nCoor, map, stepMap)
                            && !toVisit.contains(nCoor)) {
                        toVisit.add(nCoor)
                    }
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val map: MutableList<MutableList<Char>> = mutableListOf()
        input.forEach{ line -> map.add(line.replace('S', 'a').toCharArray().map{ it } as MutableList<Char>) }

        val toVisit = ArrayDeque<Coor>()
        val stepMap = Array(map.size) { Array(map[0].size) { Int.MAX_VALUE} }

        val start = Coor(-1, -1, 0)
        start@ for (i in 0 until map.size) {
            for (j in 0 until map[0].size) {
                if (map[i][j] == 'a') {
                    start.x = i
                    start.y = j
                    break@start
                }
            }
        }
        toVisit.add(start)

        while(toVisit.isNotEmpty()) {
            val steps = examineCoor2(toVisit, map, stepMap)
            if (stepMap.any{it.contains(5)}) {
//                println("########################")
//                for (i in stepMap) {
//                    i.forEach { print(it.toString().padStart(11, ' ')) }
//                    println()
//                }
            }
            if (steps != -1) {

                return steps
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../12linput")
    check(part1(testInput) == 31)

    val input = readInput("../../12input")
    println(part1(input))

    check(part2(testInput) == 29)
    println(part2(input))
}
