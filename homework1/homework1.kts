// tasks 1
fun main() {
    val firstString = "I can"
    val secondString = "code"
    println("$firstString $secondString!")
}

// task 2
fun main() {
    val myAge = 23
    val myAgeInTenYears = myAge + 10
    val daysInYear = 365.25
    val daysPassed = (myAgeInTenYears*daysInYear)
    println("Мой возраст $myAge лет. Через 10 лет, мне будет $myAgeInTenYears лет, с момента моего рождения пройдет $daysPassed дней.")
}

// task 3
import kotlin.math.sqrt

fun main() {
    val ac = 6.0
    val cb = 8.0
    val ab = Math.sqrt(ac * ac + cb * cb)
    val s = 0.5*(ac * cb)
    val p = (ab + cb +ac)
    print("площадь = $s")
    print("периметр = $p")
}
