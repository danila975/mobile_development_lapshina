package com.example.sputnik.view

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.example.sputnik.model.Instruction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }
    val coroutineScope = rememberCoroutineScope()

    val favoriteInstructions by instructionController.getFavoriteInstructions().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Избранное") })
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
            items(favoriteInstructions) { instruction ->
                FavoriteInstructionRow(
                    instruction = instruction,
                    navController = navController,
                    instructionController = instructionController
                )
            }
        }
    }
}

@Composable
fun FavoriteInstructionRow(
    instruction: Instruction,
    navController: NavController,
    instructionController: InstructionController
) {
    val coroutineScope = rememberCoroutineScope()

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
        IconButton(onClick = {
            coroutineScope.launch {
                instructionController.removeFromFavorites(instruction)
            }
        }) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить из избранного")
        }
    }
}
