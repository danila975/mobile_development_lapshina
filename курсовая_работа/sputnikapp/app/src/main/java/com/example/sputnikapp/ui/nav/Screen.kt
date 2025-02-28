// Screen.kt
package com.example.sputnikapp.ui.nav

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val args: List<NamedNavArgument> = emptyList()) {
    object Main : Screen("main")
    object Favorites : Screen("favorites")
    object Admin : Screen("admin")

    object AdminLogin : Screen(
        route = "admin_login?redirect={redirect}",
        args = listOf(
            navArgument("redirect") {
                type = NavType.StringType
                defaultValue = "admin"
            }
        )
    ) {
        fun createRoute(redirect: String = "admin") = "admin_login?redirect=$redirect"
    }

    object InstructionDetail : Screen(
        route = "instruction/{instructionId}",
        args = listOf(
            navArgument("instructionId") { type = NavType.IntType }
        )
    ) {
        fun createRoute(instructionId: Int) = "instruction/$instructionId"
    }

    object AddInstruction : Screen("add_instruction")

    object EditInstruction : Screen(
        route = "edit_instruction/{instructionId}",
        args = listOf(
            navArgument("instructionId") { type = NavType.IntType }
        )
    ) {
        fun createRoute(instructionId: Int) = "edit_instruction/$instructionId"
    }

    object AddAdministrator : Screen("add_administrator")
}