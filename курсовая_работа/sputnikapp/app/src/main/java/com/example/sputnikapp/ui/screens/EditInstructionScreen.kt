// AddInstructionScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.viewmodel.InstructionViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun EditInstructionScreen(
    navController: NavController,
    instructionId: Int,
    instructionViewModel: InstructionViewModel
) {
    val instruction by instructionViewModel.getInstructionById(instructionId).observeAsState(initial = null)

    instruction?.let {
        var sectionName by remember { mutableStateOf(it.sectionName) }      // String (Non-nullable)
        var title by remember { mutableStateOf(it.title) }                  // String (Non-nullable)
        var content by remember { mutableStateOf(it.content) }              // String (Non-nullable)
        var imageUrl by remember { mutableStateOf(it.imageUrl ?: "") }      // String (Non-nullable, default to empty string)
        var showError by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Редактировать статью", style = MaterialTheme.typography.headlineMedium)

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
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL изображения") },
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    text = "Пожалуйста, заполните все поля",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (sectionName.isBlank() || title.isBlank() || content.isBlank()) {
                        showError = true
                    } else {
                        showError = false
                        val updatedInstruction = it.copy(
                            sectionName = sectionName.trim(),
                            title = title.trim(),
                            content = content.trim(),
                            imageUrl = imageUrl.ifBlank { null }  // imageUrl может быть null
                        )
                        instructionViewModel.updateInstruction(updatedInstruction)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    instructionViewModel.deleteInstruction(it)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Удалить статью")
            }
        }
    }
}