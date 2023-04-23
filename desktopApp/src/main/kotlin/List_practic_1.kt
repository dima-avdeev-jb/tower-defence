fun List_practic_1() {

//    val list = listOf(3, 4, 5, 9)
//    var max = Int.MIN_VALUE
//    for (i in list) {
//        if (max < i) {
//            max = i
//        }
//    }
//    println()


//    for (i in list.indices) {
//        println(i)
//    }
//


//    println(list.get(1))


    val list = mutableListOf(3, 4, 5, 9)
    var x = 0
    for (i in list.indices) {

        x = list.get(i)
        x *= 2
        list.set(i, x)
        println(x)
    }
    println(list)
}

