import androidx.compose.ui.graphics.Color

class PaintGame : BaseGame() {

    override fun tick() {

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
        draw.square(500, 500, 100, Color.Black)
        draw.line(450, 550, 500, 600, Color.Black)
        draw.line(550, 550, 500, 600, Color.Black)
        draw.square(500, 500, 25, Color.Blue)
//        draw.rectangle(0, 0, 500, 500, Color.Yellow)
//        draw.square(100, 100, 200, Color.Black)
//        draw.circle(500, 700, 10, Color.Black)
//        draw.square(100, 300, 100, Color.Green)
//        draw.rectangle(100, 500, 200, 100, Color.Red)
//        draw.line(0, 0, 900, 900, Color.Red)
//        draw.image(500, 500, getImage("castle.png"), 2f)
//        draw.text(500, 900, "I am Text")T
    }

    override fun onClick(x: Double, y: Double) {

    }
}
