fun main() {
    val quantity = 22
    if (quantity <= 9) {
        print("общая стоимость товаров")
        print(quantity * 1000)
    } else if (quantity <= 19) {
        println("общая стоимость товаров")
        println(quantity * 800)
    } else {
        println("общая стоимость товаров")
        println(quantity*600)
    }
}
