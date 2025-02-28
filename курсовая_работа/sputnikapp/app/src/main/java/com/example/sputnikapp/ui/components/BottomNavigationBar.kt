// BottomNavigationBar.kt
package com.example.sputnikapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sputnikapp.ui.nav.SetupNavGraph
import com.example.sputnikapp.ui.nav.Screen


@Composable
fun BottomNavigationBar(navController: NavController, isAdmin: Boolean) {
    val items = listOf(
        Screen.Main,
        Screen.Favorites,
        Screen.Admin
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            val icon = when (screen) {
                Screen.Main -> Icons.Default.Home
                Screen.Favorites -> Icons.Default.Favorite
                Screen.Admin -> Icons.Default.AdminPanelSettings
                else -> Icons.Default.Home
            }
            val label = when (screen) {
                Screen.Main -> "Главная"
                Screen.Favorites -> "Избранное"
                Screen.Admin -> "Админ"
                else -> ""
            }

            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}