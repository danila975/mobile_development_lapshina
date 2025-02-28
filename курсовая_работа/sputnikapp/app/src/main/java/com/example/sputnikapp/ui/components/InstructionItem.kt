// InstructionItem.kt
package com.example.sputnikapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.ui.components.ConfirmDialog
import com.example.sputnikapp.ui.nav.Screen
import com.example.sputnikapp.viewmodel.InstructionViewModel

@Composable
fun InstructionItem(
    instruction: Instruction,
    navController: NavController,
    instructionViewModel: InstructionViewModel,
    isAdmin: Boolean
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        ConfirmDialog(
            title = "Удаление инструкции",
            message = "Вы уверены, что хотите удалить инструкцию \"${instruction.title}\"?",
            onConfirm = {
                instructionViewModel.deleteInstruction(instruction)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.InstructionDetail.createRoute(instruction.id))
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!instruction.imageUrl.isNullOrEmpty()) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(instruction.imageUrl)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "Изображение",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = instruction.title, style = MaterialTheme.typography.titleMedium)
            Text(text = instruction.sectionName, style = MaterialTheme.typography.bodySmall)
        }

        IconButton(onClick = {
            val updatedInstruction = instruction.copy(isFavorite = !instruction.isFavorite)
            instructionViewModel.updateInstruction(updatedInstruction)
        }) {
            if (instruction.isFavorite) {
                Icon(Icons.Default.Favorite, contentDescription = "Убрать из избранного")
            } else {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Добавить в избранное")
            }
        }

        if (isAdmin) {
            IconButton(onClick = {
                navController.navigate(Screen.EditInstruction.createRoute(instruction.id))
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать")
            }
            IconButton(onClick = {
                showDeleteDialog = true
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}