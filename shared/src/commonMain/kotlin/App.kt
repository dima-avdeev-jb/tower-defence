import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import kotlin.math.pow
import kotlin.math.sqrt

val WORLD_SIZE = 1000.0
val ROAD_SIZE = 50.0
val TOWER_SIZE = 40.0
val ATTACK_DISTANCE = 200.0
val ENEMY_SPEED = 3.0
val ENEMY_HEALTH = 60
val gameTick = mutableStateOf(0)

fun distance(tower: Tower, enemy: Enemy): Double {
    return sqrt((tower.x - enemy.x).pow(2) + (tower.y - enemy.y).pow(2))
    // sqrt = Square Root (Квадратный корень)
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
