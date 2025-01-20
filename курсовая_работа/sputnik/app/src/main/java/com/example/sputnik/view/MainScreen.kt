// MainScreen.kt
package com.example.sputnik.view

import androidx.compose.runtime.saveable.rememberSaveable
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.catch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope()

    // Состояние для секций с обработкой ошибок
    val sections by produceState(initialValue = emptyList<Section>()) {
        instructionController.getSections()
            .catch { e ->
                e.printStackTrace()
                Log.e("MainScreen", "Ошибка при получении секций", e)
                // Здесь можно уведомить пользователя
            }
            .collect {
                value = it
            }
    }

    // Состояние для инструкций с обработкой ошибок
    val instructions by produceState(initialValue = emptyList<Instruction>()) {
        instructionController.getInstructions()
            .catch { e ->
                e.printStackTrace()
                Log.e("MainScreen", "Ошибка при получении инструкций", e)
                // Здесь можно уведомить пользователя
            }
            .collect {
                value = it
            }
    }

    val expandedSections = rememberSaveable {
        mutableStateMapOf<Int, Boolean>()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Главная") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                try {
                    navController.navigate("add_instruction")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("MainScreen", "Ошибка при навигации на добавление инструкции", e)
                    // Можно уведомить пользователя об ошибке
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить инструкцию")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            if (sections.isEmpty()) {
                item {
                    Text(
                        text = "Список секций пуст",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                sections.forEach { section ->
                    val isExpanded = expandedSections.getOrElse(section.id) { true }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedSections[section.id] = !isExpanded
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = section.title,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isExpanded) "Свернуть" else "Развернуть"
                            )
                        }
                    }
                    if (isExpanded) {
                        val sectionInstructions = instructions.filter { it.sectionId == section.id }

                        if (sectionInstructions.isEmpty()) {
                            item {
                                Text(
                                    text = "Инструкции отсутствуют",
                                    modifier = Modifier.padding(start = 32.dp, bottom = 16.dp)
                                )
                            }
                        } else {
                            items(items = sectionInstructions) { instruction ->
                                InstructionItem(
                                    instruction = instruction,
                                    navController = navController,
                                    instructionController = instructionController,
                                    coroutineScope = coroutineScope
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
