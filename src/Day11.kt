fun main() {

    val DIVIDERS_MULTIPIED = (2 * 3 * 5 * 7 * 11 * 13 * 17 * 19 * 23)

    data class Monkey (val id: Int) {
        val items: MutableList<Long> = mutableListOf()
        var op = ' '
        lateinit var n: String
        var test = 0L
        var trueTest = 0
        var falseTest = 0
        var inspected = 0L
    }

    fun minimize(n: Long): Long {
        return n % DIVIDERS_MULTIPIED
    }

    fun part1(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        for (l in input) {
            val words = l.split(' ').filter{ it != "" }

            if (words.isEmpty()) {
                continue
            }
            when (words[0]) {
                "Monkey" -> {
                    val monkey = Monkey(words[1].removeSuffix(":").toInt())
                    monkeys.add(monkey)
                }
                "Starting" -> {
                    for (i in 2 until words.size) {
                        monkeys.last().items.add(words[i].removeSuffix(",").toLong())
                    }
                }
                "Operation:" -> {
                    monkeys.last().op = words[4].toCharArray()[0]
                    monkeys.last().n = words[5]
                }
                "Test:" -> monkeys.last().test = words[3].toLong()
                "If" -> {
                    if (words[1] == "true:") {
                        monkeys.last().trueTest = words[5].toInt()
                    } else {
                        monkeys.last().falseTest = words[5].toInt()
                    }
                }
            }
        }

        for (i in 0 until 20) {
            for (monkey in monkeys) {
                monkey.items.sort()

                for (item in monkey.items) {
                    monkey.inspected++
                    val n = if (monkey.n == "old") item else monkey.n.toLong()
                    val newItem = minimize((if (monkey.op == '+') item + n else item * n) / 3)
                    if (newItem % monkey.test == 0L) {
                        monkeys[monkey.trueTest].items.add(newItem)
                    } else {
                        monkeys[monkey.falseTest].items.add(newItem)
                    }
                }
                monkey.items.clear()
            }
        }

        val inspected = monkeys.map{it.inspected}.toCollection(mutableListOf())
        inspected.sortDescending()

        return inspected[0] * inspected[1]
    }

    fun part2(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        for (l in input) {
            val words = l.split(' ').filter{ it != "" }

            if (words.isEmpty()) {
                continue
            }
            when (words[0]) {
                "Monkey" -> {
                    val monkey = Monkey(words[1].removeSuffix(":").toInt())
                    monkeys.add(monkey)
                }
                "Starting" -> {
                    for (i in 2 until words.size) {
                        monkeys.last().items.add(words[i].removeSuffix(",").toLong())
                    }
                }
                "Operation:" -> {
                    monkeys.last().op = words[4].toCharArray()[0]
                    monkeys.last().n = words[5]
                }
                "Test:" -> monkeys.last().test = words[3].toLong()
                "If" -> {
                    if (words[1] == "true:") {
                        monkeys.last().trueTest = words[5].toInt()
                    } else {
                        monkeys.last().falseTest = words[5].toInt()
                    }
                }
            }
        }

        for (i in 0 until 10000) {
            for (monkey in monkeys) {
                monkey.items.sort()

                for (item in monkey.items) {
                    monkey.inspected++
                    val n = if (monkey.n == "old") item else monkey.n.toLong()
                    val newItem = minimize(if (monkey.op == '+') item + n else item * n)

                    if (newItem % monkey.test.toLong() == 0L) {
                        monkeys[monkey.trueTest].items.add(newItem)
                    } else {
                        monkeys[monkey.falseTest].items.add(newItem)
                    }
                }
                monkey.items.clear()
            }
//            if (i % 1000 == 0 || i == 20) {
//                println("== After round $i ==")
//                monkeys.forEach { println("Monkey ${it.id} ins items ${it.inspected} times") }
//            }
        }

        val inspected = monkeys.map{it.inspected}.toCollection(mutableListOf())
        inspected.sortDescending()

//        println("${inspected[0]} * ${inspected[1]} = ${inspected[0] * inspected[1]}")
        return inspected[0] * inspected[1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("../../11linput")
    check(part1(testInput) == 10605L)

    val input = readInput("../../11input")
    println(part1(input))

    check(part2(testInput) == 2713310158)
    println(part2(input))
}
