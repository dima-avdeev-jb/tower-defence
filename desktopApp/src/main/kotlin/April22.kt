fun April22() {
    var a = 1
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
    val max = findMaxValueInArray(listOf(1, 4, 2, 8, 2, 5))
    println(max)
}

fun fun1() {
    println("I am fun1")
}

fun sum(a: Int, b: Int):Int {
    return a + b
}

fun findMaxValueInArray(list: List<Int>):Int {

    return 0
}
