import kotlin.math.roundToInt
import kotlin.math.sin

// tasks 1
fun main() {
    var first: Float = 3.14F
    var second: Float = 42.0F
    var result: Double = (first + second).toDouble()
    println(result)
}

// task 2
fun main() {
    val numberOne = 9
    val numberTwo = 4
    val result = numberOne/numberTwo
    val remainder = numberOne%numberTwo
    println("При делении $numberOne на $numberTwo результат равен $result, остаток равен $remainder")
    val result2 = numberOne.toDouble()/numberTwo
    println("Результат деления $numberOne на $numberTwo равен $result2")
}

// task 3
fun main() {
    val dayOfBirth = 13
    val monthOfBirth = 11
    val yearOfBirth = 2000
    val currentDay = 16
    val currentMonth = 9
    val currentYear = 2024
    val yearsPassed = currentYear - yearOfBirth
    val monthsPassed = (yearsPassed * 12) + (currentMonth - monthOfBirth)
    val daysPassed = (yearsPassed * 360) + ((currentMonth - monthOfBirth) * 30) + (currentDay - dayOfBirth)
    val secondsPassed = daysPassed * 24 * 60 * 60
    println("$yearsPassed лет, $monthsPassed месяцев, $daysPassed дней и $secondsPassed секунд прошло с момента моего рождения")

    if (monthOfBirth in 1..3) {
        println("Я родился в первом квартале.")
    } else if (monthOfBirth in 4..6) {
        println("Я родился во втором квартале.")
    } else if (monthOfBirth in 7..9) {
        println("Я родился в третьем квартале.")
    } else {
        println("Я родился в четвёртом квартале.")
    }
}

// task4
import kotlin.math.sin
        import kotlin.math.roundToInt

fun main() {
    val angle = 1.0
    val sinValue = sin(angle)
    val roundedSin = (sinValue * 1000).roundToInt() / 1000.0
    println("Синус от 1 равен: $roundedSin")
}
