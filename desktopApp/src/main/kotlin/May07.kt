fun May07() {

    val Dog1 = Dog("Snelok")
    Dog1.woof(3)

//    val a = 123
//    println(a)
    val l:MutableList<Int> = mutableListOf<Int>(1, 2, 3)
//    println(l)
//
//    for (element in l) {
//        println(element)
//    }

//    for (index in l.indices) {
//        var LisstX2 =l.get(index)
//        LisstX2 *= 2
//        l.set(index, LisstX2)
//        println(l.get(index))
//    }
//    println(l)
//    PrintName("Dima", 12)
//    val Result = CanDrink(18)
//    if(Result == true){
//        println("You can")
//    }else{
//        println("No you can't")
//    }
//    println(Result)
}
//
//fun PrintName(Name: String, age: Int) {
//    println("Hello $Name, your age is: $age")
//}
//
//fun CanDrink(Age: Int): Boolean {
//    if (Age > 17) {
//        return true
//    } else {
//        return false
//    }
//}
class Dog(var name:String){
        fun woof(Count:Int){
            println("Woof $name $Count times")
        }
}