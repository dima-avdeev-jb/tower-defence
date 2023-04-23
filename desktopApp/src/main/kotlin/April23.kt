fun April23() {
    var a = 1 // variable - переменная
    a++
    println(a) // 2
    val b = 1 // value - значение (аналог const - константа)
    println(Int.MAX_VALUE) // 2 147 483 647
    println(Float.MAX_VALUE) // 3.4028235 E38
    println(Double.MAX_VALUE) // 1.7976931348623157E308

    val a1: Int = 1 // Int (или Integer) - целочисленное
    val a2: Float = 3.14f // Float point - плавающая точка  // 23.41432424
    val a3: Double = 3.14 // Double - число двойной точности
    val a4: Number = 1 // 3.14f , 3.14

    println(f1(2.0))
    println(Math.pow(2.0, 3.0)) // power 2 ^ 3 = 8
    Math.sqrt(4.0) // 2.0
}

fun f1(x: Double): Double {
    val y = x * x + 2 * x + 3
    return y
}
