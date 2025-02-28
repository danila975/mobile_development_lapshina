// AddInstructionScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.viewmodel.InstructionViewModel

@Composable
fun AddInstructionScreen(
    navController: NavController,
    instructionViewModel: InstructionViewModel
) {
    var sectionName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Добавить статью", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = sectionName,
            onValueChange = { sectionName = it },
            label = { Text("Секция (раздел)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Содержание") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 10
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL изображения (необязательно)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                text = "Пожалуйста, заполните все обязательные поля.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                if (sectionName.isBlank() || title.isBlank() || content.isBlank()) {
                    showError = true
                } else {
                    val instruction = Instruction(
                        sectionName = sectionName,
                        title = title,
                        content = content,
                        imageUrl = if (imageUrl.isBlank()) null else imageUrl
                    )
                    instructionViewModel.addInstruction(instruction)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Добавить")
        }
    }
}