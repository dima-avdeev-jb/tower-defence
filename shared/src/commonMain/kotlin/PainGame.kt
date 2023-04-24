import androidx.compose.ui.graphics.Color

class PaintGame : BaseGame() {

    override fun tick() {

    }

    override fun drawGame(draw: Draw) { // draw - рисовать
        draw.circle(100, 100, 20, Color.Black)
    }

    override fun onClick(x: Double, y: Double) {

    }
}
