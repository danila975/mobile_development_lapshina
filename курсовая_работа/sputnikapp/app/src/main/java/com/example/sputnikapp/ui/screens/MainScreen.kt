// MainScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.ui.components.SearchBar
import com.example.sputnikapp.ui.components.SectionItem
import com.example.sputnikapp.viewmodel.InstructionViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun MainScreen(
    navController: NavController,
    instructionViewModel: InstructionViewModel,
    isAdmin: Boolean
) {
    var searchQuery by remember { mutableStateOf("") }

    // Получаем инструкции в зависимости от поискового запроса
    val instructions by remember(searchQuery) {
        if (searchQuery.isBlank()) {
            instructionViewModel.getAllInstructions()
        } else {
            instructionViewModel.searchInstructions(searchQuery)
        }
    }.observeAsState(initial = emptyList())

    // Группируем инструкции по секциям
    val instructionsBySection = instructions.groupBy { it.sectionName }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChanged = { newQuery -> searchQuery = newQuery },
            onSearchClicked = { /* Поиск обновляется автоматически */ }
        )

        if (instructionsBySection.isNotEmpty()) {
            LazyColumn {
                items(instructionsBySection.entries.toList()) { entry ->
                    val sectionName = entry.key
                    val instructionsList = entry.value
                    SectionItem(
                        sectionName = sectionName,
                        instructions = instructionsList,
                        navController = navController,
                        instructionViewModel = instructionViewModel,
                        isAdmin = isAdmin // Передаем isAdmin
                    )
                }
            }
        } else {
            Text("Нет доступных статей.", modifier = Modifier.padding(16.dp))
        }
    }
}