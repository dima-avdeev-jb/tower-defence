fun April29() {
    practik(123)
    val cat = Cat(3)
    cat.cats()
//    println("April 29")
//    var abc123: Int = 123
//    var dist: Double = distance(1.0, 1.0, 2.0, 2.0)
//    if (dist < 5.0) {
//
//    }
//
//    val enemyies: List<Enemy> = listOf(Enemy(1, 1), Enemy(2, 2))
//    println(enemyies)
//
//    val enemy1 = Enemy(1,1)
//
//    enemy1.hello()
//    enemy1.move()
//}
//
//fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
//    val calculated = Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))
//    println(calculated)
//    return calculated
//}
//
//class Enemy(var x: Int, var y:Int) {
//    var name = "Bob"
//
//    fun hello() {
//        println("Hello, I am enemy $x $y")
//    }
//
//    fun move() {
//        x++
//        hello()
//    }
}
class Cat(val x:Int){
    fun cats(){
        println("You have $x cats")
    }
}

fun practik(x:Int) {
    println("Tvoe cislo $x")
}
