// AddAdministratorScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.data.entities.Administrator
import com.example.sputnikapp.viewmodel.AdminViewModel

@Composable
fun AddAdministratorScreen(
    navController: NavController,
    adminViewModel: AdminViewModel
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Добавить администратора", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                text = "Пожалуйста, заполните все поля.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                if (login.isBlank() || password.isBlank()) {
                    showError = true
                } else {
                    val newAdmin = Administrator(login = login, password = password)
                    adminViewModel.addAdministrator(newAdmin)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Добавить")
        }
    }
}