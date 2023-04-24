import androidx.compose.ui.graphics.Color

class PaintGame : BaseGame() {

    override fun tick() {

    }

    override fun drawGame(draw: Draw) { // draw - рисовать
        draw.circle(100, 100, 50, Color.Black)
        draw.square(100, 300, 100, Color.Green)
        draw.rectangle(100, 500, 200, 100, Color.Red)
        draw.line(300, 300, 700, 400, Color.Black)
        draw.text(0, 0, "I am Text")
        draw.image(100, 600, getImage("castle.png"))
    }

    override fun onClick(x: Double, y: Double) {

    }
}
