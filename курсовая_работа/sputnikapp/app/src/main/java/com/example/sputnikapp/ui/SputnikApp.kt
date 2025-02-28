// SputnikApp.kt
package com.example.sputnikapp.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import com.example.sputnikapp.ui.components.BottomNavigationBar
import com.example.sputnikapp.ui.nav.SetupNavGraph
import com.example.sputnikapp.viewmodel.InstructionViewModel
import com.example.sputnikapp.viewmodel.AdminViewModel

@Composable
fun SputnikApp(
    instructionViewModel: InstructionViewModel,
    adminViewModel: AdminViewModel
) {
    val navController = rememberNavController()
    val isAdmin by adminViewModel.isAuthenticated.observeAsState(false)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, isAdmin = isAdmin)
        }
    ) { innerPadding ->
        SetupNavGraph(
            navController = navController,
            instructionViewModel = instructionViewModel,
            adminViewModel = adminViewModel,
            paddingValues = innerPadding,
            isAdmin = isAdmin
        )
    }
}