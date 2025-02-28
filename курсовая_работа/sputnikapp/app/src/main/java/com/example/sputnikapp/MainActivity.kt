// MainActivity.kt
package com.example.sputnikapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sputnikapp.ui.SputnikApp
import com.example.sputnikapp.ui.theme.SputnikappTheme
import com.example.sputnikapp.viewmodel.AdminViewModel
import com.example.sputnikapp.viewmodel.InstructionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val instructionViewModel: InstructionViewModel = viewModel()
            val adminViewModel: AdminViewModel = viewModel()
            SputnikappTheme {
                SputnikApp(
                    instructionViewModel = instructionViewModel,
                    adminViewModel = adminViewModel
                )
            }
        }
    }
}