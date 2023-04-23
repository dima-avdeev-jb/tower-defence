import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer

open class BaseGame {
    open fun tick() {

    }

    open fun drawGame(draw: Draw) {

    }

    open fun onClick(x: Double, y: Double) {

    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GameView(getGame: () -> BaseGame) {
    val game = remember { mutableStateOf(getGame()) }
    val textMeasure = rememberTextMeasurer()
    Box(Modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize().pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Release) {
                        val position = event.changes[0].position
                        game.value.onClick(
                            position.x.toDouble(), position.y.toDouble()
                        )
                    }
                }
            }
        }) {
            gameTick.value++
            game.value.tick()
            game.value.drawGame(object : Draw {
                override fun text(x: Number, y: Number, text: String) {
                    drawText(textMeasure, text, Offset(x.toFloat(), y.toFloat()))
                }

                override fun image(x: Number, y: Number, image: ImageBitmap) {
                    drawImage(image, topLeft = Offset(x.toFloat(), y.toFloat()))
                }

                override fun circle(x: Number, y: Number, radius: Number, color: Color) {
                    drawCircle(color, radius.toFloat(), Offset(x.toFloat(), y.toFloat()))
                }

                override fun rectangle(x: Number, y: Number, width: Number, height: Number, color: Color) {
                    drawRect(color, topLeft = Offset(x.toFloat(), y.toFloat()), size = Size(width.toFloat(), height.toFloat()))
                }

                override fun line(x1: Number, y1: Number, x2: Number, y2: Number, color: Color, width: Number) {
                    drawLine(color, Offset(x1.toFloat(), y1.toFloat()), Offset(x2.toFloat(), y2.toFloat()), strokeWidth = width.toFloat())
                }
            })
        }

        Row(Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = {
                game.value = getGame()
            }) {
                Text("Restart")
            }
        }
    }

}

interface Draw {
    fun text(x: Number, y: Number, text: String)
    fun image(x: Number, y: Number, image: ImageBitmap)
    fun circle(x: Number, y: Number, radius: Number, color: Color)
    fun rectangle(x: Number, y: Number, width: Number, height: Number, color: Color)
    fun square(centerX: Number, centerY: Number, size: Number, color: Color) {
        rectangle(centerX.toFloat() - size.toFloat() / 2, centerY.toFloat() - size.toFloat() / 2, size, size, color)
    }

    fun line(x1: Number, y1: Number, x2: Number, y2: Number, color: Color, width: Number = 4f)
}
