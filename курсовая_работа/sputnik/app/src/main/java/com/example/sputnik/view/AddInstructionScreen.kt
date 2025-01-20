// AddInstructionScreen.kt
package com.example.sputnik.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInstructionScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope()

    var sectionTitle by remember { mutableStateOf("") }
    var instructionTitle by remember { mutableStateOf("") }
    var instructionContent by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить инструкцию") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.semantics { contentDescription = "Назад" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = sectionTitle,
                onValueChange = { sectionTitle = it },
                label = { Text("Секция") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = instructionTitle,
                onValueChange = { instructionTitle = it },
                label = { Text("Название инструкции") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = instructionContent,
                onValueChange = { instructionContent = it },
                label = { Text("Текст инструкции") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                singleLine = false,
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (sectionTitle.isBlank() || instructionTitle.isBlank() || instructionContent.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Пожалуйста, заполните все поля")
                        }
                    } else {
                        coroutineScope.launch {
                            try {
                                val sectionId = sectionTitle.hashCode().absoluteValue
                                val instructionId = instructionTitle.hashCode().absoluteValue

                                // Получаем текущие секции
                                val existingSections = instructionController.getSections().first()

                                // Проверяем, существует ли секция с таким ID
                                if (existingSections.none { section -> section.id == sectionId }) {
                                    val newSection = Section(id = sectionId, title = sectionTitle)
                                    instructionController.addSection(newSection)
                                }

                                val newInstruction = Instruction(
                                    id = instructionId,
                                    sectionId = sectionId,
                                    title = instructionTitle,
                                    content = instructionContent
                                )

                                instructionController.addInstruction(newInstruction)
                                navController.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                snackbarHostState.showSnackbar("Ошибка при сохранении инструкции")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}
