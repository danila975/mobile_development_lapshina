// task4
fun main() {
    var height = 0
    var days = 0

    while (height < 10) {
        height += 2
        days++
        if (height < 10) {
            height--
        }
    }

    println(days)
}