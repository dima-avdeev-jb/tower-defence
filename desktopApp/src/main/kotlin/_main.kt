import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

object Main {
    //Array - массив
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size > 0) {
            val str = args.get(0)
            if (str == "game") {
                application {
                    Window(state = rememberWindowState(width = 900.dp, height = 900.dp), onCloseRequest = ::exitApplication) {
                        App()
                    }
                }
            }
        } else {
            println("----------------------------------------------")
            lessons()
            println("----------------------------------------------")
        }
    }
}

fun lessons() {
//    April22()
//    April23()
//    List_theory_1()
//    List_practic_1()
//    Class_theory_1()
//    Class_practice_1()
//    Type_teory()
//    LoopTeory_1()
//    IfElse_teory_1()
    April29()
}
