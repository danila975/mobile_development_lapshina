// AppNavigation.kt
package com.example.sputnik

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.sputnik.view.*
import androidx.compose.foundation.layout.padding

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {

        // Главный экран
        composable("main") {
            MainScreen(navController)
        }

        // Экран избранных инструкций
        composable("favorites") {
            FavoritesScreen(navController)
        }

        // Экран деталей инструкции
        composable(
            route = "instruction_detail/{instructionId}",
            arguments = listOf(navArgument("instructionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val instructionId = backStackEntry.arguments?.getInt("instructionId")
            if (instructionId != null) {
                InstructionDetailScreen(navController, instructionId)
            } else {
                // Обработка случая, когда instructionId отсутствует или некорректен
                Log.e("AppNavigation", "Некорректный или отсутствующий instructionId")
                // Показываем экран ошибки
                InstructionErrorScreen(navController, "Инструкция не найдена")
            }
        }

        // Экран добавления инструкции
        composable("add_instruction") {
            AddInstructionScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionErrorScreen(navController: NavController, errorMessage: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ошибка") },
                navigationIcon = {
                    IconButton(onClick = {
                        try {
                            navController.popBackStack()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("InstructionErrorScreen", "Ошибка при возврате назад", e)
                            // Можно уведомить пользователя об ошибке
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
