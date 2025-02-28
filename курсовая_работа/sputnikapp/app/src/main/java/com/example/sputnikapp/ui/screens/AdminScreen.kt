// AdminScreen.Kt
package com.example.sputnikapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sputnikapp.ui.components.SectionItem
import com.example.sputnikapp.ui.components.ConfirmDialog
import com.example.sputnikapp.ui.components.AdministratorItem
import com.example.sputnikapp.ui.nav.Screen
import com.example.sputnikapp.viewmodel.AdminViewModel
import com.example.sputnikapp.viewmodel.InstructionViewModel
import androidx.compose.runtime.livedata.observeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    instructionViewModel: InstructionViewModel,
    adminViewModel: AdminViewModel
    ) {
    // Проверка аутентификации администратора
    val isAuthenticated by adminViewModel.isAuthenticated.observeAsState(false)

    // Если не аутентифицирован, перенаправить на экран входа администратора
    if (!isAuthenticated) {
        LaunchedEffect(Unit) {
            navController.navigate(
                Screen.AdminLogin.createRoute(
                    redirect = navController.currentDestination?.route ?: Screen.Admin.route
                )
            ) {
                popUpTo(Screen.Admin.route) { inclusive = true }
            }
        }
    } else {
        var searchQuery by remember { mutableStateOf("") }

        // Состояние для выбранной вкладки: 0 - Инструкции, 1 - Администраторы
        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf("Инструкции", "Администраторы")

        // Получаем инструкции и администраторов в зависимости от выбранной вкладки
        val instructions by instructionViewModel.getAllInstructions().observeAsState(initial = emptyList())
        val filteredInstructions = instructions.filter { instruction ->
            instruction.title.contains(searchQuery, ignoreCase = true) ||
                    instruction.sectionName.contains(searchQuery, ignoreCase = true)
        }
        val instructionsBySection = filteredInstructions.groupBy { instruction -> instruction.sectionName }

        val administrators by adminViewModel.searchAdministrators(searchQuery).observeAsState(initial = emptyList())

        // Переменная состояния для отображения диалога удаления секции
        var sectionToDelete by remember { mutableStateOf<String?>(null) }

        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // Поле поиска
            var searchActive by remember { mutableStateOf(false) }

            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* Обработка поиска */ },
                active = searchActive,
                onActiveChange = { searchActive = it },
                placeholder = { Text("Поиск") },
                content = { }
            )

            // Кнопка добавления в зависимости от выбранной вкладки
            if (selectedTab == 0) { // Инструкции
                Button(
                    onClick = { navController.navigate(Screen.AddInstruction.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Добавить инструкцию")
                }
            } else { // Администраторы
                Button(
                    onClick = { navController.navigate(Screen.AddAdministrator.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Добавить администратора")
                }
            }

            // Отображение контента в зависимости от выбранной вкладки
            if (selectedTab == 0) {
                // Отображение списка инструкций
                LazyColumn {
                    instructionsBySection.forEach { (sectionName, instructionsInSection) ->
                        item {
                            SectionItem(
                                sectionName = sectionName,
                                instructions = instructionsInSection,
                                navController = navController,
                                instructionViewModel = instructionViewModel,
                                isAdmin = true,
                                onDeleteSection = {
                                    sectionToDelete = sectionName
                                }
                            )
                        }
                    }
                }
            } else {
                // Отображение списка администраторов
                LazyColumn {
                    items(administrators) { admin ->
                        AdministratorItem(
                            admin = admin,
                            adminViewModel = adminViewModel
                        )
                    }
                }
            }

            // Кнопка управления администраторами убираем, так как теперь управление в отдельной вкладке
            /*
            Button(
                onClick = { navController.navigate(Screen.ManageAdministrators.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Управление администраторами")
            }
            */

            Button(
                onClick = { adminViewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Выйти")
            }
        }

        // Отображение диалога подтверждения удаления секции
        if (sectionToDelete != null) {
            ConfirmDialog(
                title = "Удалить секцию",
                message = "Вы уверены, что хотите удалить секцию \"$sectionToDelete\" и все её инструкции?",
                onConfirm = {
                    instructionViewModel.deleteSection(sectionToDelete!!)
                    sectionToDelete = null
                },
                onDismiss = {
                    sectionToDelete = null
                }
            )
        }
    }
    }
