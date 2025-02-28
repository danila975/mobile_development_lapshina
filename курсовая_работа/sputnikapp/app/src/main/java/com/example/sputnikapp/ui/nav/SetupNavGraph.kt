package com.example.sputnikapp.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sputnikapp.ui.screens.*
import com.example.sputnikapp.viewmodel.AdminViewModel
import com.example.sputnikapp.viewmodel.InstructionViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    instructionViewModel: InstructionViewModel,
    adminViewModel: AdminViewModel,
    paddingValues: PaddingValues,
    isAdmin: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                navController = navController,
                instructionViewModel = instructionViewModel,
                isAdmin = isAdmin
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                navController = navController,
                instructionViewModel = instructionViewModel,
                isAdmin = isAdmin
            )
        }
        composable(Screen.Admin.route) {
            AdminScreen(
                navController = navController,
                adminViewModel = adminViewModel,
                instructionViewModel = instructionViewModel
            )
        }
        composable(
            route = Screen.InstructionDetail.route,
            arguments = Screen.InstructionDetail.args
        ) { backStackEntry ->
            val instructionId = backStackEntry.arguments?.getInt("instructionId") ?: 0
            InstructionDetailScreen(
                navController = navController,
                instructionId = instructionId,
                instructionViewModel = instructionViewModel,
                isAdmin = isAdmin
            )
        }
        composable(Screen.AddInstruction.route) {
            AddInstructionScreen(
                navController = navController,
                instructionViewModel = instructionViewModel
            )
        }
        composable(
            route = Screen.EditInstruction.route,
            arguments = Screen.EditInstruction.args
        ) { backStackEntry ->
            val instructionId = backStackEntry.arguments?.getInt("instructionId") ?: 0
            EditInstructionScreen(
                navController = navController,
                instructionId = instructionId,
                instructionViewModel = instructionViewModel
            )
        }
        composable(Screen.AddAdministrator.route) {
            AddAdministratorScreen(
                navController = navController,
                adminViewModel = adminViewModel
            )
        }
        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(
                navController = navController,
                adminViewModel = adminViewModel
            )
        }
    }
}