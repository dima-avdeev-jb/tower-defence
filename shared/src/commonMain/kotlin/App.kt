import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

val WORLD_SIZE = 1000.0
val ROAD_SIZE = 50.0
val TOWER_SIZE = 40.0
val ATTACK_DISTANCE = 200.0
val ENEMY_SPEED = 3.0
val ENEMY_HEALTH = 60
val gameTick = mutableStateOf(0)

fun distance(tower: Tower, enemy: Enemy): Double {
    return sqrt((tower.x - enemy.x).pow(2) + (tower.y - enemy.y).pow(2))
}

@Composable
fun App() {
    Box(Modifier.fillMaxSize()) {
        GameView {
            TowerDefenceGame()
        }
    }
    Box(Modifier.fillMaxSize()) {

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

class TowerDefenceGame : Game() {
    var enemyScore: Int = 0
    var yourScore: Int = 0
    val enemies = mutableListOf<Enemy>()
    val towers = mutableListOf<Tower>()

    override fun tick() {
        val roadBegin = roadBlocks.first()
        if (Random.nextInt(20) == 0) {
            enemies += Enemy(
                roadBegin.x + Random.nextDouble(ROAD_SIZE),
                roadBegin.y + Random.nextDouble(ROAD_SIZE),
            )
        }
        val removeEnemies = mutableListOf<Enemy>()
        yourScore += enemies.count { it.health <= 0 }
        enemies.removeAll { it.health <= 0 }
        enemies.forEach { enemy ->
            val currentRoadIndex = roadBlocks.indexOfFirst {
                enemy.x >= it.x && enemy.x <= it.x + ROAD_SIZE && enemy.y >= it.y && enemy.y <= it.y + ROAD_SIZE
            }
            val nextRoadBlock = roadBlocks.getOrNull(currentRoadIndex + 1)
            if (nextRoadBlock != null) {
                enemy.x += (nextRoadBlock.centerX - enemy.x).coerceIn(-ENEMY_SPEED, ENEMY_SPEED)
                enemy.y += (nextRoadBlock.centerY - enemy.y).coerceIn(-ENEMY_SPEED, ENEMY_SPEED)
            } else {
                enemyScore++
                removeEnemies.add(enemy)
            }
        }
        enemies.removeAll(removeEnemies)
        towers.forEach { tower ->
            val nearestEnemy = enemies.sortedBy { distance(tower, it) }.firstOrNull()
            if (nearestEnemy != null) {
                if (distance(tower, nearestEnemy) < ATTACK_DISTANCE) {
                    tower.attack = nearestEnemy
                    nearestEnemy.health--
                } else {
                    tower.attack = null
                }
            } else {
                tower.attack = null
            }
        }
    }

    override fun drawGame(draw: Draw) {
        draw.text(0, 0, "You: $yourScore, Enemy: $enemyScore")
        for (road in roadBlocks) {
            draw.rectangle(
                road.i * ROAD_SIZE, road.j * ROAD_SIZE,
                ROAD_SIZE, ROAD_SIZE,
                Color.Gray,
            )
        }
        for (enemy in enemies) {
            val enemyColor =
                Color(
                    red = (1f - enemy.health.toFloat() / ENEMY_HEALTH).coerceIn(0f, 1f),
                    green = 0f,
                    blue = 0f
                )
            draw.circle(enemy.x, enemy.y, 20, enemyColor)
        }
        for (tower in towers) {
            draw.square(
                tower.x,
                tower.y,
                TOWER_SIZE,
                Color.Blue
            )
            val attack = tower.attack
            if (attack != null) {
                draw.line(
                    tower.x, tower.y,
                    attack.x, attack.y,
                    Color.Green,
                )
            }
        }
        draw.image(450f, 950f, getImage("castle.png"))
    }

    override fun onClick(x: Double, y: Double) {
        towers += Tower(x, y, null)
    }
}

open class Game {
    open fun tick() {

    }

    open fun drawGame(draw: Draw) {

    }

    open fun onClick(x: Double, y: Double) {

    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GameView(getGame: () -> Game) {
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

class Enemy(var x: Double, var y: Double, var health: Int = ENEMY_HEALTH)
class Tower(val x: Double, val y: Double, var attack: Enemy?)
data class RoadBlock(val i: Int, val j: Int)

val RoadBlock.x get() = i * ROAD_SIZE
val RoadBlock.y get() = j * ROAD_SIZE
val RoadBlock.centerX get() = i * ROAD_SIZE + ROAD_SIZE / 2
val RoadBlock.centerY get() = j * ROAD_SIZE + ROAD_SIZE / 2

val roadBlocks = buildList {
    // Это создаются квадратики для дороги
    val s = (WORLD_SIZE / ROAD_SIZE).roundToInt()
    add(RoadBlock(s / 2, 0))
    add(RoadBlock(s / 2, 1))
    add(RoadBlock(s / 2, 2))
    for (x in (2..s / 2).reversed()) {
        add(RoadBlock(x, 3))
    }
    for (y in 4..s / 2) {
        add(RoadBlock(2, y))
    }
    for (x in 2..s - 2) {
        add(RoadBlock(x, s / 2))
    }
    for (y in s / 2..s - 4) {
        add(RoadBlock(s - 2, y))
    }
    for (x in (s / 2..s - 2).reversed()) {
        add(RoadBlock(x, s - 4))
    }
    add(RoadBlock(s / 2, s - 3))
    add(RoadBlock(s / 2, s - 2))
    add(RoadBlock(s / 2, s - 1))
}.distinct().toList()
