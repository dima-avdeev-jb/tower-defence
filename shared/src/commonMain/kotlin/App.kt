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
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

val WORLD_SIZE = 1000f
val ROAD_SIZE = 50f
val TOWER_SIZE = 40f
val ATTACK_DISTANCE = 200f
val ENEMY_SPEED = 3f
val ENEMY_HEALTH = 60

class GameState {
    val ticks = mutableStateOf(0)
    var enemyScore: Int = 0
    var yourScore: Int = 0
    val enemies = mutableListOf<Enemy>()
    val towers = mutableListOf<Tower>()

    fun tick() {
        val roadBegin = roadBlocks.first()
        if (Random.nextInt(20) == 0) {
            enemies += Enemy(
                roadBegin.x + Random.nextDouble(ROAD_SIZE.toDouble()).toFloat(),
                roadBegin.y + Random.nextDouble(ROAD_SIZE.toDouble()).toFloat(),
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
}

var gameState: GameState = GameState()

fun distance(tower: Tower, enemy: Enemy): Float {
    return sqrt((tower.x - enemy.x).pow(2) + (tower.y - enemy.y).pow(2))
}

@Composable
fun App() {
    Box(Modifier.fillMaxSize()) {
        GameField()
    }
    Box(Modifier.fillMaxSize()) {
        Row(Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = {
                gameState = GameState()
            }) {
                Text("Restart")
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GameField() {
    val rememberTextMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    density.density
    Canvas(Modifier.fillMaxSize().pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.type == PointerEventType.Release) {
                    val position = event.changes[0].position
                    gameState.towers += Tower(position.x, position.y, null)
                }
            }
        }
    }) {
        gameState.ticks.value++
        gameState.tick()
        drawText(
            rememberTextMeasurer,
            "You: ${gameState.yourScore}, Enemy: ${gameState.enemyScore}",
            Offset(0f, 0f)
        )
        for (road in roadBlocks) {
            drawRect(
                Color.Gray,
                Offset(road.i * ROAD_SIZE, road.j * ROAD_SIZE),
                Size(ROAD_SIZE, ROAD_SIZE)
            )
        }
        for (enemy in gameState.enemies) {
            val enemyColor =
                Color(
                    red = (1f - enemy.health.toFloat() / ENEMY_HEALTH).coerceIn(0f, 1f),
                    green = 0f,
                    blue = 0f
                )
            drawCircle(enemyColor, radius = 10f, center = Offset(enemy.x, enemy.y))
        }
        for (tower in gameState.towers) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(tower.x - TOWER_SIZE / 2, tower.y - TOWER_SIZE / 2),
                size = Size(TOWER_SIZE, TOWER_SIZE)
            )
            val attack = tower.attack
            if (attack != null) {
                drawLine(
                    Color.Green,
                    start = Offset(tower.x, tower.y),
                    end = Offset(attack.x, attack.y),
                    strokeWidth = 4f
                )
            }
        }
        drawImage(getImage("castle.png"), Offset(450f, 950f))
    }
}

class Enemy(var x: Float, var y: Float, var health: Int = ENEMY_HEALTH)
class Tower(val x: Float, val y: Float, var attack: Enemy?)
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
