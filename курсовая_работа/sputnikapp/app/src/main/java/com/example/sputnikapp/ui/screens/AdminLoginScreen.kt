// AdminLoginScreen.kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.ui.nav.Screen
import com.example.sputnikapp.viewmodel.AdminViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AdminLoginScreen(
    navController: NavController,
    adminViewModel: AdminViewModel
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var authAttempted by remember { mutableStateOf(false) }

    // Получаем аргумент 'redirect'
    val redirectBackStackEntry = remember(navController) {
        navController.getBackStackEntry(Screen.AdminLogin.route)
    }
    val redirectRoute = redirectBackStackEntry.arguments?.getString("redirect") ?: Screen.Admin.route

    // Отслеживаем статус аутентификации
    val isAuthenticated by adminViewModel.isAuthenticated.observeAsState(false)

    // Реагируем на изменение isAuthenticated
    LaunchedEffect(isAuthenticated, authAttempted) {
        if (isAuthenticated) {
            // Если аутентификация успешна, переходим на нужный экран
            showError = false
            navController.navigate(redirectRoute) {
                popUpTo(Screen.AdminLogin.route) { inclusive = true }
            }
        } else if (authAttempted) {
            // Если попытка аутентификации была, но isAuthenticated всё ещё false, отображаем ошибку
            showError = true
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Пожалуйста, авторизуйтесь", style = MaterialTheme.typography.headlineMedium)

        if (showError) {
            Text(
                text = "Вы ввели неправильный логин или пароль.\nПожалуйста, повторите авторизацию.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (login.isNotBlank() && password.isNotBlank()) {
                    authAttempted = true // Указываем, что была попытка аутентификации
                    adminViewModel.authenticate(login, password)
                } else {
                    showError = true
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Войти")
        }
    }
}