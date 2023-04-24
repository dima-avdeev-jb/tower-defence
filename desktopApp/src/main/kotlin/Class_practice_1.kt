fun Class_practice_1() {
    val p = PrintLapsAndName(10, "Cat")
    p.laps()
}

class PrintLapsAndName(var age: Int, var name: String) {
    fun laps() {

        println("You car have  $age yers old, and his name $name ")
    }
}
