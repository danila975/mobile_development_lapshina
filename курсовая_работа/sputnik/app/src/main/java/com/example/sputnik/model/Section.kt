// Section
package com.example.sputnik.model

import kotlinx.serialization.Serializable

@Serializable
data class Section(
    val id: Int,
    val title: String
) {
    init {
        require(id >= 0) { "ID секции не может быть отрицательным" }
        require(title.isNotBlank()) { "Название секции не может быть пустым" }
    }
}
