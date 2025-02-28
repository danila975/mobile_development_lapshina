// AdminViewModel.kt
package com.example.sputnikapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.sputnikapp.data.AppDatabase
import com.example.sputnikapp.data.entities.Administrator
import com.example.sputnikapp.data.repositories.AdministratorRepository
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AdministratorRepository

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    init {
        val adminDao = AppDatabase.getInstance(application).administratorDao()
        repository = AdministratorRepository(adminDao)
    }

    fun authenticate(login: String, password: String) = viewModelScope.launch {
        val admin = repository.authenticate(login, password)
        _isAuthenticated.value = (admin != null)
    }

    fun logout() {
        _isAuthenticated.value = false
    }

    fun addAdministrator(admin: Administrator) = viewModelScope.launch {
        repository.addAdministrator(admin)
    }

    fun deleteAdministrator(admin: Administrator) = viewModelScope.launch {
        repository.deleteAdministrator(admin)
    }

    fun getAllAdministrators() = repository.getAllAdministrators().asLiveData()

    fun searchAdministrators(query: String) = repository.searchAdministrators(query).asLiveData()
}
