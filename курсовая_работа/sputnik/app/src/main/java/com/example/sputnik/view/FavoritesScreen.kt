// FavoritesScreen.kt
package com.example.sputnik.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp // Добавили импорт для dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope() // Создаём CoroutineScope здесь

    val favoriteInstructions by produceState(initialValue = emptyList<Instruction>()) {
        instructionController.getFavoriteInstructions()
            .catch { e ->
                e.printStackTrace()
                Log.e("FavoritesScreen", "Ошибка при получении избранных инструкций", e)
                // Можно обновить значение по умолчанию или уведомить пользователя
            }
            .collect { instructions ->
                value = instructions
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") }
            )
        }
    ) { paddingValues ->
        if (favoriteInstructions.isEmpty()) {
            Text(
                text = "Список избранных инструкций пуст",
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(items = favoriteInstructions) { instruction ->
                    InstructionItem(
                        instruction = instruction,
                        navController = navController,
                        instructionController = instructionController,
                        coroutineScope = coroutineScope // Передаём coroutineScope
                    )
                }
            }
        }
    }
}
