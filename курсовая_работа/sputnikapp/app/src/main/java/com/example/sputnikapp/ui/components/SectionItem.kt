// SectionItem.kt
package com.example.sputnikapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // Добавлено
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete // Добавлено
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.viewmodel.InstructionViewModel

@Composable
fun SectionItem(
    sectionName: String,
    instructions: List<Instruction>,
    navController: NavController,
    instructionViewModel: InstructionViewModel,
    isAdmin: Boolean,
    onDeleteSection: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column {
            ListItem(
                headlineContent = { Text(sectionName) }, // Исправлено
                trailingContent = {
                    Row {
                        if (onDeleteSection != null) {
                            IconButton(onClick = onDeleteSection) {
                                Icon(Icons.Default.Delete, contentDescription = "Удалить секцию")
                            }
                        }
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = "Развернуть секцию"
                            )
                        }
                    }
                }
            )
            if (expanded) {
                instructions.forEach { instruction ->
                    InstructionItem(
                        instruction = instruction,
                        navController = navController,
                        instructionViewModel = instructionViewModel,
                        isAdmin = isAdmin
                    )
                }
            }
        }
    }
}