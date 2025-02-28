// FavoritesScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp // Добавили импорт dp
import androidx.navigation.NavController
import com.example.sputnikapp.ui.components.InstructionItem
import com.example.sputnikapp.ui.components.SearchBar
import com.example.sputnikapp.viewmodel.InstructionViewModel
import androidx.compose.runtime.livedata.observeAsState // Добавили импорт observeAsState

@Composable
fun FavoritesScreen(
    navController: NavController,
    instructionViewModel: InstructionViewModel,
    isAdmin: Boolean
) {
    var searchQuery by remember { mutableStateOf("") }

    val favoriteInstructions by remember(searchQuery) {
        if (searchQuery.isBlank()) {
            instructionViewModel.getFavoriteInstructions()
        } else {
            instructionViewModel.searchFavoriteInstructions(searchQuery)
        }
    }.observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChanged = { newQuery -> searchQuery = newQuery },
            onSearchClicked = { /* Поиск обновляется автоматически */ }
        )

        if (favoriteInstructions.isEmpty()) {
            Text("У Вас нет избранных материалов.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(favoriteInstructions) { instruction ->
                    InstructionItem(
                        instruction = instruction,
                        navController = navController,
                        instructionViewModel = instructionViewModel,
                        isAdmin = isAdmin
                    )
                }
            }
        }
    }
}