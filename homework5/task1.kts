// task1
fun main() {
    val deposit = 500000.0f
    val period = 5
    val rate = 0.11f
    var totalAmount = deposit

    for (i in 1..period) {
        totalAmount = totalAmount + (totalAmount*rate)
    }

val profit = totalAmount - deposit
    println("Сумма вклада через $period лет увеличится на ${String.format("%.2f", profit)} и составит ${String.format("%.2f", totalAmount)} рублей")
}
