// SearchBar.kt
package com.example.sputnikapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Поиск") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onSearchClicked) {
                Icon(Icons.Default.Search, contentDescription = "Искать")
            }
        }
    )
}