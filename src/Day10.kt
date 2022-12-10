fun main() {

    fun part1(input: List<String>): Int {
        var pc = 0
        var x = 1
        var ss = 0

        fun checkSs() {
            if ((pc - 20) % 40 == 0) {
                ss += pc * x
//                println("pc: $pc, x: $x, ss: $ss, Δss: ${pc*x}")
            }
        }

        for (l in input) {
            pc++
            checkSs()
            if (l[0] == 'a') {
                pc++
                checkSs()
                x += l.split(' ')[1].toInt()
            }
        }
//        println(ss)
        return ss
    }

    fun part2(input: List<String>): String {
        var pc = 0
        var x = 1
        var screen = StringBuilder()

        fun drawPixel() {
            if (pc % 40 == 0 && pc != 0) {
                screen.append('\n')
            }
            if (x - (pc % 40) <= 1 && x - (pc % 40) >= -1) {
                screen.append('#')
            } else {
                screen.append('.')
            }
        }

        for (l in input) {
            drawPixel()
            pc++
            if (l[0] == 'a') {
                drawPixel()
                pc++
                x += l.split(' ')[1].toInt()
            }
        }
        return screen.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../10linput")
    check(part1(testInput) == 13140)

    val input = readInput("../../10input")
    println(part1(input))

    check(part2(testInput).equals("##..##..##..##..##..##..##..##..##..##..\n" +
            "###...###...###...###...###...###...###.\n" +
            "####....####....####....####....####....\n" +
            "#####.....#####.....#####.....#####.....\n" +
            "######......######......######......####\n" +
            "#######.......#######.......#######....."))
    println(part2(input))
}
