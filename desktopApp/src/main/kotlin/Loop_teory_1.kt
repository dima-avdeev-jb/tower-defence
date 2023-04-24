// Теория циклов
fun LoopTeory_1() {

    for (i in 0..3) { // Цикл от 0 до 3
        println(i)
        // 0
        // 1
        // 2
        // 3
    }

    val listA = listOf("a", "b", "c")

    for (element in listA) { // Цикл по элементам списка listA
        println(element)
        // a
        // b
        // c
    }

    for (index in listA.indices) { // Цикл по индексам списка listA
        println(index)
        // 0
        // 1
        // 2
    }

    var a = 1
    if (a < 5) {
        println("inside if")
    }
    while (a <= 5) {
        println("inside while, a = $a")
        a++
    }
}
