fun main() {
    var a = 1
    var str = "Hello"
    var list = listOf(1, 2, 3)
    for (i in 0..10) {
        if (i < 5) {
            println("i < 5")
        } else {
            println("i >= 5")
        }
        when(i) {
            0 -> println("Zero")
            in 2..4 -> println("2..4")
            else -> {
                // some code
            }
        }
    }
}
