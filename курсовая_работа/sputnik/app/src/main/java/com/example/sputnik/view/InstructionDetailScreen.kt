package com.example.sputnik.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnik.controller.InstructionController
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionDetailScreen(navController: NavController, instructionId: Int) {
    val context = LocalContext.current
    val instructionController = remember { InstructionController(context) }

    val instructions by instructionController.getInstructions().collectAsState(initial = emptyList())
    val instruction = instructions.find { it.id == instructionId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(instruction?.title ?: "Инструкция") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        instruction?.let {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = it.content)
            }
        }
    }
}
