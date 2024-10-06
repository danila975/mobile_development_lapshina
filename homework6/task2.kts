// task2
fun sum(vararg numbers: Int): Int {
    var sum = 0
    for (number in numbers) {
        sum += number
    }
    return sum
}

fun main() {
    val numbers1 = intArrayOf(1, 2, 3, 4, 5)
    val numbers2 = intArrayOf(6, 7, 8, 9, 10)
    val totalSum = sum(*numbers1, *numbers2)
    println("Общая сумма: $totalSum")
}

