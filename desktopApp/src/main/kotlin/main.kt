import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

//Array - массив
fun main(args: Array<String>) {
    if (args.size > 0) {
        val str = args.get(0)
        if (str == "game") {
            application {
                Window(onCloseRequest = ::exitApplication) {
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

fun lessons() {
    April22()
}
