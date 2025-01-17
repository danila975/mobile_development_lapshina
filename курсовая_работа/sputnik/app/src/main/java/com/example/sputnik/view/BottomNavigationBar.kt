package com.example.sputnik.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute(navController) == "main",
            onClick = { navController.navigate("main") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Главная") },
            label = { Text("Главная") }
        )
        NavigationBarItem(
            selected = currentRoute(navController) == "favorites",
            onClick = { navController.navigate("favorites") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Избранное") },
            label = { Text("Избранное") }
        )
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val currentBackStackEntry = navController.currentBackStackEntry
    return currentBackStackEntry?.destination?.route
}
