// task3
import kotlin.random.Random
fun main() {
    for (i in 1..10) {
        val randomNum = Random.nextInt(1, 11)
        if (randomNum == 5) {
            println("Чтобы выпало число 5 понадобилось $i итераций")
            break
        }
    }
}
