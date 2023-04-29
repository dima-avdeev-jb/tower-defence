import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt
import kotlin.random.Random

class TowerDefenceGame : BaseGame() {
    var enemyScore: Int = 0
    var yourScore: Int = 0
    val enemies = mutableListOf<Enemy>()
    val towers = mutableListOf<Tower>()
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

    override fun drawGame(draw: Draw) { // draw - рисовать
        draw.text(100, 950, "You: $yourScore, Enemy: $enemyScore")
        for (road in roadBlocks) { // Рисуем дорогу
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
        draw.image(500, 950f, getImage("castle.png"))
    }

    override fun onMouseMove(x: Double, y: Double) {

    }

    override fun onMouseDown(x: Double, y: Double) {

    }

    override fun onMouseUp(x: Double, y: Double) {
        towers += Tower(x, y, null)
    }
}

class Enemy(var x: Double, var y: Double, var health: Int = ENEMY_HEALTH)
class Tower(val x: Double, val y: Double, var attack: Enemy?)
data class RoadBlock(val i: Int, val j: Int)

val RoadBlock.x get() = i * ROAD_SIZE
val RoadBlock.y get() = j * ROAD_SIZE
val RoadBlock.centerX get() = i * ROAD_SIZE + ROAD_SIZE / 2
val RoadBlock.centerY get() = j * ROAD_SIZE + ROAD_SIZE / 2
