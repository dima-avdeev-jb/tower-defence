import androidx.compose.ui.graphics.Color

class House(val x: Double, val y: Double) {

}

class PaintGame : BaseGame() {
    val houses: MutableList<House> = mutableListOf(House(500.0, 500.0))
    val enemies: MutableList<Enemy1> = mutableListOf(Enemy1(1.0, 1.0), Enemy1(100.0, 1.0))
    var cat1 = Cat(250, 50)
    override fun onMouseDown(x: Double, y: Double) {
        houses.add(House(x, y))
        // Размещать домик
    }

    override fun drawGame(draw: Draw) { // draw - рисовать
        for (i in 0..10) {
            val x = i * 100
            draw.line(x, 0, x, 1000, Color.Blue.copy(0.3f), 1)
            draw.text(x, 50, "$x")
        }
        for (i in 0..10) {
            val y = i * 100
            draw.line(0, y, 1000, y, Color.Blue.copy(0.3f), 1)
            draw.text(50, y, "$y")
        }
        for (i in 1..9) {
            drawHouse(draw, i * 100, 100, false)
        }
        for (i in 1..9) {
            drawHouse(draw, i * 100, 300, false)
        }
        for (house in houses) {
            drawHouse(draw, house.x, house.y, false)
        }

        for (e in enemies) {
            draw.circle(e.x, e.y, 25, Color.Red)
        }
        draw.circle(cat1.x, cat1.y, 50, Color.Blue)
    }

    fun drawHouse(draw: Draw, x: Number, y: Number, transparent: Boolean) {
        val xd: Double = x.toDouble()
        val yd: Double = y.toDouble()
        var alpha = 1.0f
        if (transparent == true) {
            alpha = 0.3f
        }
        draw.square(xd + 50, yd + 50, 100, Color.Black.copy(alpha), false)
        draw.line(xd + 0, yd + 100, xd + 50, yd + 150, Color.Black.copy(alpha))
        draw.line(xd + 100, yd + 100, xd + 50, yd + 150, Color.Black.copy(alpha))
        draw.square(xd + 50, yd + 50, 25, Color.Blue.copy(alpha), false)
    }

    override fun tick() {
        for (e in enemies) {
            e.move()
        }
    }

}

class Enemy1(var x: Double, var y: Double) {
    fun move() {
        y += 1

    }
}
class Cat(var x:Int, var y:Int){

}