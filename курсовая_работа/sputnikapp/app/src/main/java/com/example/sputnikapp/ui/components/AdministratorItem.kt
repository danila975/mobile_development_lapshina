// AdministratorItem.kt
package com.example.sputnikapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sputnikapp.data.entities.Administrator
import com.example.sputnikapp.viewmodel.AdminViewModel
import androidx.compose.material3.HorizontalDivider

@Composable
fun AdministratorItem(
    admin: Administrator,
    adminViewModel: AdminViewModel
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        ConfirmDialog(
            title = "Удаление администратора",
            message = "Вы уверены, что хотите удалить администратора \"${admin.login}\"?",
            onConfirm = {
                adminViewModel.deleteAdministrator(admin)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    ListItem(
        headlineContent = { Text(admin.login) },
        trailingContent = {
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить администратора")
            }
        }
    )
    HorizontalDivider()
}