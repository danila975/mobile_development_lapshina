// InstructionDetailScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sputnikapp.viewmodel.InstructionViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.ui.components.ConfirmDialog  // Импортируем ваш ConfirmDialog

@Composable
fun InstructionDetailScreen(
    navController: NavController,
    instructionId: Int,
    instructionViewModel: InstructionViewModel,
    isAdmin: Boolean
) {
    // Получаем инструкцию по ID
    val instruction by instructionViewModel.getInstructionById(instructionId).observeAsState()

    // Проверяем, что инструкция не равна null
    instruction?.let { currentInstruction ->
        var showDeleteDialog by remember { mutableStateOf(false) }
        var isFavorite by remember { mutableStateOf(currentInstruction.isFavorite) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Верхняя строка с кнопками
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Кнопка "Закрыть"
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.Close, contentDescription = "Закрыть")
                }

                Row {
                    // Кнопка "Избранное"
                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        instructionViewModel.updateInstruction(
                            currentInstruction.copy(isFavorite = isFavorite)
                        )
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = if (isFavorite) "Удалить из избранного" else "Добавить в избранное"
                        )
                    }

                    if (isAdmin) {
                        // Кнопка "Редактировать"
                        IconButton(onClick = {
                            navController.navigate("edit_instruction/${currentInstruction.id}")
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                        }
                        // Кнопка "Удалить" с подтверждением
                        IconButton(onClick = {
                            showDeleteDialog = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                        }
                    }
                }
            }

            // Заголовок инструкции
            Text(
                text = currentInstruction.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Изображение инструкции, если есть URL
            if (!currentInstruction.imageUrl.isNullOrEmpty()) {
                val painter = rememberAsyncImagePainter(currentInstruction.imageUrl)
                Image(
                    painter = painter,
                    contentDescription = "Изображение",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 8.dp)
                )
            }

            // Содержание инструкции
            Text(text = currentInstruction.content, style = MaterialTheme.typography.bodyLarge)
        }

        // Используем ваш компонент ConfirmDialog
        if (showDeleteDialog) {
            ConfirmDialog(
                title = "Удалить инструкцию",
                message = "Вы уверены, что хотите удалить эту инструкцию?",
                onConfirm = {
                    instructionViewModel.deleteInstruction(currentInstruction)
                    showDeleteDialog = false
                    navController.popBackStack()
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    } ?: run {
        // Если инструкция равна null, отображаем индикатор загрузки
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}