// task3
fun isEven(number: Int): Boolean {
    return number % 2 == 0
}

fun isDivisibleBy3(number: Int): Boolean {
    return number % 3 == 0
}

fun generateArray(x: Int, y: Int): IntArray {
    val array = IntArray(y - x + 1) { it + x }
    return array
}

fun filterEven(array: IntArray): IntArray {
    return array.filter { !isEven(it) }.toIntArray()
}

fun filterDivisibleBy3(array: IntArray): IntArray {
    return array.filter { !isDivisibleBy3(it) }.toIntArray()
}

fun main() {
    val numbers = generateArray(1, 100)
    val filteredEven = filterEven(numbers)
    val filteredDivisibleBy3 = filterDivisibleBy3(numbers)
    println("Массив без четных чисел: ${filteredEven.joinToString(",")}")
    println("Массив без чисел кратных трем: ${filteredDivisibleBy3.joinToString(",")}")
}