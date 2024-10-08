fun main() {
    println("Введите порядковый номер пальца (1-5):")
    val fingerNumber = readLine()?.toIntOrNull()
    if (fingerNumber != null) {
        if (fingerNumber == 1) {
            println("Большой палец")
        } else if (fingerNumber == 2) {
            println("Указательный палец")
        } else if (fingerNumber == 3) {
            println("Средний палец")
        } else if (fingerNumber == 4) {
            println("Безымянный палец")
        } else if (fingerNumber == 5) {
            println("Мизинец")
        } else {
            println("Некорректный номер пальца. Пожалуйста, введите число от 1 до 5.")
        }
    } else {
        println("Некорректный ввод. Пожалуйста, введите число.")
    }
}