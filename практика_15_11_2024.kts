
class Baggage {
    var weight: Double = 0.0
        set(value) {
            if (value > 30.0) {
                println("Предупреждение: вес багажа не может превышать 30 кг. Установленное значение: $field кг.")
            } else {
                field = value
            }
        }
}

data class Passenger(val name: String, val age: Int)

fun main() {
    // Задача 2: Контроль багажа
    val baggage = Baggage()
    baggage.weight = 25.0  // Установит вес 25 кг
    println("Вес багажа: ${baggage.weight} кг")
    baggage.weight = 35.0  // Попытка установки 35 кг, выдаст предупреждение
    println("Вес багажа после попытки установить 35 кг: ${baggage.weight} кг")

    // Задача 3: Фильтрация и подсчет пассажиров
    val passengers = listOf(
        Passenger("Иван", 20),
        Passenger("Мария", 17),
        Passenger("Петр", 21),
        Passenger("Анна", 16)
    )

    val filteredPassengers = passengers.filter { it.age > 18 }
    val count = filteredPassengers.count()
    val names = filteredPassengers.map { it.name }

    println("Количество пассажиров старше 18 лет: $count")
    println("Имена пассажиров: $names")
}
