// task1
fun main() {
    val Data = Triple("данила",  23, 1000000)
    val name = Data.first
    val age = Data.second
    val favoriteNumber = Data.third
    println("Имя: $name, Возраст: $age, Любимое число: $favoriteNumber")
}

// task2
fun main() {
    val hasTicket = true
    val isAdult = true
    val isRegistered = false
    val isVIP = true
    if (hasTicket && isAdult && (isRegistered || isVIP)) {
        println("вы можете войти") }
    else {
        println("вы не можете войти") }
    }