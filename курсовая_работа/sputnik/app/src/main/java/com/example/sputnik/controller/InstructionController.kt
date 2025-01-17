package com.example.sputnik.controller

import android.content.Context
import com.example.sputnik.data.DataStoreManager
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class InstructionController(context: Context) {

    private val dataStoreManager = DataStoreManager(context)

    // Инструкции
    fun getInstructions(): Flow<List<Instruction>> = dataStoreManager.getInstructions()

    suspend fun addInstruction(instruction: Instruction) {
        val currentInstructions = getInstructions().first()
        dataStoreManager.saveInstructions(currentInstructions + instruction)
    }

    suspend fun deleteInstruction(instruction: Instruction) {
        val currentInstructions = getInstructions().first()
        dataStoreManager.saveInstructions(currentInstructions - instruction)
    }

    // Фавориты
    suspend fun addToFavorites(instruction: Instruction) {
        updateInstructionFavoriteStatus(instruction, true)
    }

    suspend fun removeFromFavorites(instruction: Instruction) {
        updateInstructionFavoriteStatus(instruction, false)
    }

    fun getFavoriteInstructions(): Flow<List<Instruction>> {
        return getInstructions().map { instructions ->
            instructions.filter { it.isFavorite }
        }
    }

    private suspend fun updateInstructionFavoriteStatus(instruction: Instruction, isFavorite: Boolean) {
        val currentInstructions = getInstructions().first()
        val updatedInstructions = currentInstructions.map {
            if (it.id == instruction.id) it.copy(isFavorite = isFavorite) else it
        }
        dataStoreManager.saveInstructions(updatedInstructions)
    }

    // Секции
    fun getSections(): Flow<List<Section>> = dataStoreManager.getSections()

    suspend fun addSection(section: Section) {
        val currentSections = getSections().first()
        dataStoreManager.saveSections(currentSections + section)
    }
}
