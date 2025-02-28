// Section.kt
package com.example.sputnikapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey val name: String
)