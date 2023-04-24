fun IfElse_teory_1() {

    var a = 3
    if (a == 2) {
        println("Hot")
    } else if (a == 3) {
        println("Success")
    } else if (a == 4) {
        println("Hot")
    } else {
        println("Cold")
    }

    var c = 3
    when (c) {
        2 -> {
            println("Hot")
        }

        3 -> {
            println("Success")
        }

        4 -> {
            println("Hot")
        }

        else -> {
            println("Cold")
        }
    }

}
