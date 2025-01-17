package com.example.sputnik.model

import kotlinx.serialization.Serializable

@Serializable
data class Section(
    val id: Int,
    val title: String
)
