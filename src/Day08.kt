fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableList<List<Int>> = mutableListOf()
        input.forEach{ line -> map.add(line.toCharArray().map{it.digitToInt()})}

        var v = 0

        for (i in map.indices) {
            for (j in map[0].indices) {
                if (i == 0 || j == 0 || i == map.size-1 || j == map[0].size-1) {
                    v++
                    continue
                }
                val height = map[i][j]
                val visible = arrayOf(true, true, true, true)
                for (k in 0 until i) {
                    if (map[k][j] >= height) {
                        visible[0] = false
                        break
                    }
                }
                for (k in i+1 until map.size) {
                    if (map[k][j] >= height) {
                        visible[1] = false
                        break
                    }
                }
                for (k in 0 until j) {
                    if (map[i][k] >= height) {
                        visible[2] = false
                        break
                    }
                }
                for (k in j+1 until map[0].size) {
                    if (map[i][k] >= height) {
                        visible[3] = false
                        break
                    }
                }
                if (visible.any{it}) {
                    v++
                }
            }
        }
        return v
    }

    fun part2(input: List<String>): Int {
        val map: MutableList<List<Int>> = mutableListOf()
        input.forEach{ line -> map.add(line.toCharArray().map{it.digitToInt()})}

        val score = Array(map.size) { Array(map[0].size) {0} }

        for (i in map.indices) {
            for (j in map[0].indices) {
                if (i == 0 || j == 0 || i == map.size-1 || j == map[0].size-1) {
                    continue
                }
                val height = map[i][j]
                var d1 = 0
                for (k in i-1 downTo 0) {
                    d1++
                    if (map[k][j] >= height) {
                        break
                    }
                }
                var d2 = 0
                for (k in i+1 until map.size) {
                    d2++
                    if (map[k][j] >= height) {
                        break
                    }
                }
                var d3 = 0
                for (k in j-1 downTo  0) {
                    d3++
                    if (map[i][k] >= height) {
                        break
                    }
                }
                var d4 = 0
                for (k in j+1 until map[0].size) {
                    d4++
                    if (map[i][k] >= height) {
                        break
                    }
                }
                score[i][j] = d1 * d2 * d3 * d4
            }
        }
        return score.maxOf { it.max() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../8linput")
    check(part1(testInput) == 21)

    val input = readInput("../../8input")
    println(part1(input))

    check(part2(testInput) == 8)
    println(part2(input))
}
