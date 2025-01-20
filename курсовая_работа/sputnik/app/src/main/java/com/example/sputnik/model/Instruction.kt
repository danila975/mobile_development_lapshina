// Instruction.kt
package com.example.sputnik.model

import kotlinx.serialization.Serializable

@Serializable
data class Instruction(
    val id: Int,
    val sectionId: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
) {
    init {
        require(id >= 0) { "ID инструкции не может быть отрицательным" }
        require(sectionId >= 0) { "ID секции не может быть отрицательным" }
        require(title.isNotBlank()) { "Заголовок инструкции не может быть пустым" }
        require(content.isNotBlank()) { "Содержание инструкции не может быть пустым" }
    }
}
