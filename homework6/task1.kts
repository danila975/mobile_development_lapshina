// task1
fun main() {
    val results = mapOf(
        "Салават Юлаев" to arrayOf("3:6", "5:5", "N/A"),
        "Авангард" to arrayOf("2:1"),
        "АкБарс" to arrayOf("3:3", "1:2")
    )

    for ((opponent, scores) in results) {
        for (score in scores) {
            println("Игра с $opponent - $score")
        }
    }
}
