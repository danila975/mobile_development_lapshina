package com.example.sputnik.view

import com.example.sputnik.model.Instruction
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import com.example.sputnik.model.Section
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope()

    val sections by instructionController.getSections().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Главный экран") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addInstruction")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить инструкцию")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(sections) { section ->
                SectionItem(section, navController, instructionController)
            }
        }
    }
}

@Composable
fun SectionItem(
    section: Section,
    navController: NavController,
    instructionController: InstructionController
) {
    val instructions by instructionController.getInstructions().collectAsState(initial = emptyList())
    val sectionInstructions = instructions.filter { it.sectionId == section.id }
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = section.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp)
        )
        if (isExpanded) {
            sectionInstructions.forEach { instruction ->
                InstructionRow(
                    instruction = instruction,
                    navController = navController,
                    instructionController = instructionController
                )
            }
        }
    }
}

@Composable
fun InstructionRow(
    instruction: Instruction,
    navController: NavController,
    instructionController: InstructionController
) {
    val coroutineScope = rememberCoroutineScope()
    var isFavorite: Boolean by remember { mutableStateOf(instruction.isFavorite) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("instructionDetail/${instruction.id}")
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = instruction.title)
        Row {
            IconButton(onClick = {
                coroutineScope.launch {
                    if (isFavorite) {
                        instructionController.removeFromFavorites(instruction)
                    } else {
                        instructionController.addToFavorites(instruction)
                    }
                    isFavorite = !isFavorite
                }
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Удалить из избранного" else "Добавить в избранное"
                )
            }
            IconButton(onClick = {
                coroutineScope.launch {
                    instructionController.deleteInstruction(instruction)
                }
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить инструкцию")
            }
        }
    }
}
