import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

open class BaseGame {
    open fun tick() {

    }

    open fun drawGame(draw: Draw) {

    }

    open fun onMouseMove(x: Double, y: Double) {

    }

    open fun onMouseDown(x: Double, y: Double) {

    }

    open fun onMouseUp(x: Double, y: Double) {

    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GameView(getGame: () -> BaseGame) {
    val virtualSize = 1000f
    val sizeState = remember { mutableStateOf(Size(1000f, 1000f)) }
    val realSize by derivedStateOf { minOf(sizeState.value.width, sizeState.value.height) }
    val game = remember { mutableStateOf(getGame()) }
    val textMeasure = rememberTextMeasurer()
    Box(Modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize().pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()

                    if (event.changes.size > 0) {
                        val position = event.changes[0].position

                        val y = sizeState.value.height - position.y.toDouble()
                        val x = position.x.toDouble()

                        when (event.type) {
                            PointerEventType.Move -> {
                                game.value.onMouseMove(
                                    x / realSize * virtualSize,
                                    y / realSize * virtualSize,
                                )
                            }

                            PointerEventType.Press -> {
                                game.value.onMouseDown(
                                    x / realSize * virtualSize,
                                    y / realSize * virtualSize,
                                )
                            }

                            PointerEventType.Release -> {
                                game.value.onMouseUp(
                                    x / realSize * virtualSize,
                                    y / realSize * virtualSize,
                                )
                            }
                        }
                    }
                }
            }
        }) {
            sizeState.value = size
            fun Number.real() = toFloat() / virtualSize * realSize
            gameTick.value++
            game.value.tick()
            game.value.drawGame(object : Draw {
                override fun text(x: Number, y: Number, text: String, backgroundColor: Color) {
                    val measure = textMeasure.measure(text).size
                    val y = virtualSize - y.toFloat()
                    val x = x.toFloat()
                    val backgroundPadding = 2f
                    drawRect(
                        backgroundColor,
                        Offset(
                            x.real() - backgroundPadding - measure.width.toFloat() / 2,
                            y.real() - backgroundPadding - measure.height.toFloat() / 2
                        ),
                        Size(
                            measure.width.toFloat() + backgroundPadding * 2,
                            measure.height.toFloat() + backgroundPadding * 2
                        )
                    )
                    drawText(textMeasure, text, Offset(x.real() - measure.width / 2, y.real() - measure.height / 2))
                }

                override fun image(x: Number, y: Number, image: ImageBitmap, scale: Number) {
                    val w = image.width * scale.toFloat()
                    val h = image.height * scale.toFloat()
                    val y = virtualSize - y.toFloat()
                    val dstOffset = IntOffset(
                        x = (x.toFloat() - w.toFloat() / 2).real().toInt(),
                        y = (y.toFloat() - h.toFloat() / 2).real().toInt()
                    )
                    drawImage(
                        image = image,
                        srcSize = IntSize(image.width, image.height),
                        dstOffset = dstOffset,
                        dstSize = IntSize(w.real().toInt(), h.real().toInt())
                    )
                }

                override fun circle(x: Number, y: Number, radius: Number, color: Color, fill: Boolean, breadth: Number) {
                    val y = virtualSize - y.toFloat()
                    drawCircle(
                        color = color,
                        radius = radius.real(),
                        center = Offset(x.real(), y.real()),
                        style = if (fill) Fill else Stroke(width = breadth.real())
                    )
                }

                override fun rectangle(x: Number, y: Number, width: Number, height: Number, color: Color, fill: Boolean, breadth: Number) {
                    val y = virtualSize - y.toFloat() - height.toFloat()
                    drawRect(
                        color = color,
                        topLeft = Offset(x.real(), y.real()),
                        size = Size(width.real(), height.real()),
                        style = if (fill) Fill else Stroke(width = breadth.real())
                    )
                }

                override fun line(x1: Number, y1: Number, x2: Number, y2: Number, color: Color, breadth: Number) {
                    val y1 = virtualSize - y1.toFloat()
                    val y2 = virtualSize - y2.toFloat()
                    drawLine(color, Offset(x1.real(), y1.real()), Offset(x2.real(), y2.real()), strokeWidth = breadth.real())
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
    fun text(x: Number, y: Number, text: String, backgroundColor: Color = Color.White)
    fun image(x: Number, y: Number, image: ImageBitmap, scale: Number = 1f)
    fun circle(x: Number, y: Number, radius: Number, color: Color, fill: Boolean = true, breadth: Number = 4f)
    fun rectangle(x: Number, y: Number, width: Number, height: Number, color: Color, fill: Boolean = true, breadth: Number = 4f)
    fun square(centerX: Number, centerY: Number, size: Number, color: Color, fill: Boolean = true, breadth: Number = 4f) {
        rectangle(centerX.toFloat() - size.toFloat() / 2, centerY.toFloat() - size.toFloat() / 2, size, size, color, fill, breadth)
    }

    fun line(x1: Number, y1: Number, x2: Number, y2: Number, color: Color, breadth: Number = 4f)
}
