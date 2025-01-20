// InstructionDetailScreen.kt
package com.example.sputnik.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import kotlinx.coroutines.flow.catch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionDetailScreen(navController: NavController, instructionId: Int) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }

    val instruction by produceState<Instruction?>(initialValue = null) {
        instructionController.getInstructionById(instructionId)
            .catch { e ->
                e.printStackTrace()
                Log.e("InstructionDetailScreen", "Ошибка при получении инструкции", e)
                // Здесь можно уведомить пользователя об ошибке
            }
            .collect {
                value = it
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = instruction?.title ?: "Инструкция")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            try {
                                navController.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.e("InstructionDetailScreen", "Ошибка при возврате назад", e)
                                // Можно уведомить пользователя об ошибке
                            }
                        },
                        modifier = Modifier.semantics { contentDescription = "Назад" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        instruction?.let { currentInstruction ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = currentInstruction.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } ?: run {
            // Обрабатываем случай, когда инструкция не найдена
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Инструкция не найдена.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        onClick = {
                            try {
                                navController.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.e("InstructionDetailScreen", "Ошибка при возврате назад", e)
                                // Можно уведомить пользователя об ошибке
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Вернуться назад")
                    }
                }
            }
        }
    }
}
