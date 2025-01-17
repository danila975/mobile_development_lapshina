package com.example.sputnik.model

data class Instruction(
    val id: Int,
    val sectionId: Int,
    val title: String,
    val content: String,
    var isFavorite: Boolean = false
)
