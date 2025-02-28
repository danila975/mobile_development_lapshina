// InstructionViewModel.kt
package com.example.sputnikapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.sputnikapp.data.AppDatabase
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.data.repositories.InstructionRepository
import kotlinx.coroutines.launch

class InstructionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: InstructionRepository

    init {
        val instructionDao = AppDatabase.getInstance(application).instructionDao()
        repository = InstructionRepository(instructionDao)
    }

    // Ваши существующие методы:
    fun getAllInstructions() = repository.getAllInstructions().asLiveData()

    fun getFavoriteInstructions() = repository.getFavoriteInstructions().asLiveData()

    fun getInstructionById(id: Int): LiveData<Instruction?> {
        return repository.getInstructionById(id).asLiveData()
    }

    fun getSections() = repository.getSections().asLiveData()

    fun getInstructionsBySection(sectionName: String) =
        repository.getInstructionsBySection(sectionName).asLiveData()

    fun searchInstructions(query: String) = repository.searchInstructions(query).asLiveData()

    fun searchFavoriteInstructions(query: String) =
        repository.searchFavoriteInstructions(query).asLiveData()

    fun addInstruction(instruction: Instruction) = viewModelScope.launch {
        repository.addInstruction(instruction)
    }

    fun updateInstruction(instruction: Instruction) = viewModelScope.launch {
        repository.updateInstruction(instruction)
    }

    fun deleteInstruction(instruction: Instruction) = viewModelScope.launch {
        repository.deleteInstruction(instruction)
    }

    // Добавляем нужный метод:
    fun deleteSection(sectionName: String) = viewModelScope.launch {
        repository.deleteSection(sectionName)
    }
}