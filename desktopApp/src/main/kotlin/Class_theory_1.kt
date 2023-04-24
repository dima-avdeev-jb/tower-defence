// Это объявление класса
class Person(var name: String, var age: Int) { // Функция-конструтор
    fun printNameAndAge() {
        println(name + " " + age)
    }
}

fun Class_theory_1() {
    var p = Person("Dima", 35) // Вызываем функцию-конструктор
    p.printNameAndAge() // Вызов функции класса
    val b: List<Person> = listOf(Person("Petya", 20), Person("Sam", 12)) // Список объектов класса Person
}
