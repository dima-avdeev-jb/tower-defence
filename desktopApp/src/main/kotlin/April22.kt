fun April22() {
    var str = "Hello"
    var list = listOf(1, 2, 3)
    for (i in 0..10) {
        if (i < 5) {
            println("i < 5")
        } else {
            println("i >= 5")
        }
        when (i) {
            0 -> println("Zero")
            in 2..4 -> println("2..4")
            else -> {
                // some code
            }
        }
    }
    fun1()
    val c = sum(1, 2)
    println(c)
    println(sum(1, 2))
    val max = findMaxValueInArray(listOf(-12, -3, 4, 8, 2, 5))
    println(max)
    var list1 = listOf(1, 2, 3)
    var list2 = listOf(4, 2, 4)
    var list3 = listOf(2, 5, 0)
    val max2 = findMaxInMax(listOf(list1, list2, list3))
    println(max2)
}

fun findMaxInMax(listOfList: List<List<Int>>): Int {

    return 0
}

fun fun1() {
    println("I am fun1")
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun findMaxValueInArray(list: List<Int>): Int {
    var a = list.get(0)
    for (i in list) {
        if (a < i) {
            a = i
        }
    }
    return a
}
