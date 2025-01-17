package com.example.sputnik

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.sputnik.view.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("favorites") { FavoritesScreen(navController) }
        composable(
            "instructionDetail/{instructionId}",
            arguments = listOf(navArgument("instructionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val instructionId = backStackEntry.arguments?.getInt("instructionId") ?: 0
            InstructionDetailScreen(navController, instructionId)
        }
        composable("addInstruction") { AddInstructionScreen(navController) }
    }
}
