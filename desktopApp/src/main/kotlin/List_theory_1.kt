fun List_theory_1() {
    val list: List<String> = listOf("a", "b", "c")
    println(list) // [a, b, c]
                   // 0  1  2 - indexes
    println(list.get(0))
    println(list.size)
    for (element in list) {
        println(element)
        // a
        // b
        // c
    }
    for (i in list.indices) { // пройти по индексам
        println(i)
        // 0
        // 1
        // 2
    }
    for (i in 0..(list.size - 1)) {
        println(i)
        // 0
        // 1
        // 2
    }

    val mutableList: MutableList<Int> = mutableListOf(10, 20, 30)
    mutableList.set(0, 5)
    println(mutableList) // [5, 20, 30]
    mutableList.add(40) //  [5, 20, 30, 40]
    mutableList.remove(20) // [5, 30, 40]
    mutableList.removeAt(0) // [30, 40]
}

//fun privet(name: String, age: Int): String {
//    var old: String = ""
//    if (age < 5) {
//        old = "baby"
//    }
//    val c = "Hello $name $old, you are $age years"
//    return c
//}
