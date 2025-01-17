package com.example.sputnik.view

import kotlinx.coroutines.flow.first
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInstructionScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope()

    var sectionTitle by remember { mutableStateOf("") }
    var instructionTitle by remember { mutableStateOf("") }
    var instructionContent by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить инструкцию") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = sectionTitle,
                onValueChange = { sectionTitle = it },
                label = { Text("Секция") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = instructionTitle,
                onValueChange = { instructionTitle = it },
                label = { Text("Название инструкции") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = instructionContent,
                onValueChange = { instructionContent = it },
                label = { Text("Текст инструкции") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        val sectionId = sectionTitle.hashCode()
                        // Добавляем секцию, если ее нет
                        val existingSections = instructionController.getSections().first()
                        if (existingSections.none { it.id == sectionId }) {
                            val newSection = Section(id = sectionId, title = sectionTitle)
                            instructionController.addSection(newSection)
                        }
                        // Добавляем инструкцию
                        val newInstruction = Instruction(
                            id = generateUniqueId(),
                            sectionId = sectionId,
                            title = instructionTitle,
                            content = instructionContent
                        )
                        instructionController.addInstruction(newInstruction)
                        // Возвращаемся назад
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text("Сохранить")
            }
        }
    }
}

fun generateUniqueId(): Int {
    return (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
}
