import androidx.compose.ui.graphics.Color

class PaintGame : BaseGame() {

    override fun tick() {

    }

    override fun drawGame(draw: Draw) { // draw - рисовать
        draw.rectangle(0, 0, 500, 500, Color.Yellow)
        draw.square(100, 100, 200, Color.Black)
        draw.circle(500, 700, 10, Color.Black)
        draw.square(100, 300, 100, Color.Green)
        draw.rectangle(100, 500, 200, 100, Color.Red)
        draw.line(0, 0, 900, 900, Color.Red)
        draw.image(500, 500, getImage("castle.png"), 2f)
        draw.text(500, 900, "I am Text")
    }

    override fun onClick(x: Double, y: Double) {

    }
}
