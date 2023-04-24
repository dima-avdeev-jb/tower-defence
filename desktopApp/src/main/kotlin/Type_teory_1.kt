fun Type_teory() {
    var a: Int = 123 // Переменная с названием  "a" типа Int
    val b: Int = 123 // переменная (константа val) типа Int
    val c = "Hello" // Переменная типа String
    var f: Float = 3.14f // типа Float
    var d: Double = 3.14 // типа Double (в 2 раза больше чем Float)
    var n: Number = 3.14 // Может быть Int, Float, Double
    val list: List<Int> = listOf(1, 2, 3) // список объектов типа Int

    var bool: Boolean = true // false  - переменная типа Boolean

    if (bool == true) {
        println("good, bool is true")
    } else {
        println("bool is False !!!!")
    }
}

fun convertFloatToDouble(f: Float) {
    val d: Double = f.toDouble()
}

fun convertFloatToString(f: Float) {
    val str: String = f.toString()
}

