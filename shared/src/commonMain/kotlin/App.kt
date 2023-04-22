import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

val ROAD_SIZE = 50f
val WORLD_SIZE = 1000f
val TOWER_SIZE = 40f
val UNIT_SPEED = 10f
val enemies = mutableListOf<Enemy>()
val towers = mutableListOf<Tower>()
val gameTicks = mutableStateOf(0)

suspend fun gameLogic() {
    val roadBegin = roadBlocks.first()
    while (true) {
        gameTicks.value = gameTicks.value + 1
        delay(1000 / 60)
        if (Random.nextInt(20) == 0) {
            enemies += Enemy(
                roadBegin.x + Random.nextDouble(ROAD_SIZE.toDouble()).toFloat(),
                roadBegin.y + Random.nextDouble(ROAD_SIZE.toDouble()).toFloat(),
            )
        }
        val removeEnemies = mutableListOf<Enemy>()
        enemies.forEach { enemy ->
            val currentRoadIndex = roadBlocks.indexOfFirst {
                enemy.x >= it.x && enemy.x <= it.x + ROAD_SIZE && enemy.y >= it.y && enemy.y <= it.y + ROAD_SIZE
            }
            val nextRoadBlock = roadBlocks.getOrNull(currentRoadIndex + 1)
            if (nextRoadBlock != null) {
                enemy.x += (nextRoadBlock.centerX - enemy.x).coerceIn(-UNIT_SPEED, UNIT_SPEED)
                enemy.y += (nextRoadBlock.centerY - enemy.y).coerceIn(-UNIT_SPEED, UNIT_SPEED)
            } else {
                removeEnemies.add(enemy)
            }
        }
        enemies.removeAll(removeEnemies)
    }
}

@Composable
fun App() {
    LaunchedEffect(Unit) {
        gameLogic()
    }
    Box(Modifier.fillMaxSize()) {
        GameField()
    }
}

@Composable
fun GameField() {
    val density = LocalDensity.current
    density.density
    Canvas(Modifier.fillMaxSize().pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.type == PointerEventType.Release) {
                    val position = event.changes[0].position
                    towers += Tower(position.x, position.y)
                }
            }
        }
    }) {
        gameTicks.value
        for (road in roadBlocks) {
            drawRect(
                Color.DarkGray,
                Offset(road.i * ROAD_SIZE, road.j * ROAD_SIZE),
                Size(ROAD_SIZE, ROAD_SIZE)
            )
        }
        for (enemy in enemies) {
            drawCircle(Color.Red, radius = 10f, center = Offset(enemy.x, enemy.y))
        }
        for (tower in towers) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(tower.x - TOWER_SIZE / 2, tower.y - TOWER_SIZE / 2),
                size = Size(TOWER_SIZE, TOWER_SIZE)
            )
        }
    }
}

class Enemy(var x: Float, var y: Float)
data class Tower(val x: Float, val y: Float)
data class RoadBlock(val i: Int, val j: Int)

val RoadBlock.x get() = i * ROAD_SIZE
val RoadBlock.y get() = j * ROAD_SIZE
val RoadBlock.centerX get() = i * ROAD_SIZE + ROAD_SIZE / 2
val RoadBlock.centerY get() = j * ROAD_SIZE + ROAD_SIZE / 2

val roadBlocks = sequence {
    // Это создаются квадратики для дороги
    val s = (WORLD_SIZE / ROAD_SIZE).roundToInt()
    yield(RoadBlock(s / 2, 0))
    yield(RoadBlock(s / 2, 1))
    yield(RoadBlock(s / 2, 2))
    for (x in (2..s / 2).reversed()) {
        yield(RoadBlock(x, 3))
    }
    for (y in 4..s / 2) {
        yield(RoadBlock(2, y))
    }
    for (x in 2..s - 2) {
        yield(RoadBlock(x, s / 2))
    }
    for (y in s / 2..s - 4) {
        yield(RoadBlock(s - 2, y))
    }
    for (x in (s / 2..s - 2).reversed()) {
        yield(RoadBlock(x, s - 4))
    }
    yield(RoadBlock(s / 2, s - 3))
    yield(RoadBlock(s / 2, s - 2))
    yield(RoadBlock(s / 2, s - 1))
}.distinct().toList()
