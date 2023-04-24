// Это объявление класса
class Person(var name: String, var age: Int) { // Функция-конструтор
    fun printNameAndAge() {
        println(name + " " + age)
    }

    fun canDrinkAclohol(): Boolean {
        if (age >= 18) {
            return true
        } else {
            return false
        }
    }

    fun doAHomeWork(lesson: String) {
        println(name + "doing " + lesson)
    }
}

fun Class_theory_1() {
    var p = Person("Dima", 35) // Вызываем функцию-конструктор
    p.printNameAndAge() // Вызов функции класса
    val bool = p.canDrinkAclohol()
    if (bool == true) {
        println("You can drink alco")
    } else {
        println("You can't")
    }
    p.doAHomeWork("History")
    println("I am ${p.name} i am ${p.age} years old")
    val b: List<Person> = listOf(Person("Petya", 20), Person("Sam", 12)) // Список объектов класса Person
}

