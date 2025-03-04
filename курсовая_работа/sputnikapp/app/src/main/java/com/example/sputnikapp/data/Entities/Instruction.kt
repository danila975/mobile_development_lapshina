// Instruction.kt
package com.example.sputnikapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instructions")
data class Instruction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var sectionName: String,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false,
    var imageUrl: String? = null
)