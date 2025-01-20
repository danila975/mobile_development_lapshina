// InstructionItem.kt
package com.example.sputnik.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionItem(
    instruction: Instruction,
    navController: NavController,
    instructionController: InstructionController,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var isFavorite by remember { mutableStateOf(instruction.isFavorite) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clickable {
                    try {
                        navController.navigate("instruction_detail/${instruction.id}")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("InstructionItem", "Ошибка при навигации", e)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Ошибка при переходе к инструкции")
                        }
                    }
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = instruction.title ?: "Без названия",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = instruction.content ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                if (isFavorite) {
                                    instructionController.removeFromFavorites(instruction.id)
                                    isFavorite = false
                                    snackbarHostState.showSnackbar("Удалено из избранного")
                                } else {
                                    instructionController.addToFavorites(instruction.id)
                                    isFavorite = true
                                    snackbarHostState.showSnackbar("Добавлено в избранное")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.e("InstructionItem", "Ошибка при обновлении избранного", e)
                                snackbarHostState.showSnackbar("Не удалось обновить избранное")
                            }
                        }
                    }
                ) {
                    val favoriteIcon = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    }
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = if (isFavorite) "Убрать из избранного" else "Добавить в избранное"
                    )
                }
            }
        }
    }
}
