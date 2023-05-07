fun May07() {
//    val a = 123
//    println(a)
    val l = listOf<Int>(1, 2, 3)
//    println(l)
//
//    for (element in l) {
//        println(element)
//    }

    for (index in l.indices) {
        println(l.get(index))
    }
    PrintName("Dima", 12)
    val Result = CanDrink(18)
    if(Result == true){
        println("You can")
    }else{
        println("No you can't")
    }
    println(Result)
}

fun PrintName(Name: String, age: Int) {
    println("Hello $Name, your age is: $age")
}

fun CanDrink(Age: Int): Boolean {
    if (Age > 17) {
        return true
    } else {
        return false
    }
}
