// Administrator.kt
package com.example.sputnikapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "administrators")
data class Administrator(
    @PrimaryKey val login: String,
    val password: String
)