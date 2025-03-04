// InstructionRepository.kt
package com.example.sputnikapp.data.repositories

import com.example.sputnikapp.data.dao.InstructionDao
import com.example.sputnikapp.data.entities.Instruction
import kotlinx.coroutines.flow.Flow

class InstructionRepository(private val instructionDao: InstructionDao) {

    // Существующие методы
    suspend fun addInstruction(instruction: Instruction) {
        instructionDao.addInstruction(instruction)
    }

    suspend fun deleteInstruction(instruction: Instruction) {
        instructionDao.deleteInstruction(instruction)
    }

    suspend fun updateInstruction(instruction: Instruction) {
        instructionDao.updateInstruction(instruction)
    }

    fun getAllInstructions(): Flow<List<Instruction>> = instructionDao.getAllInstructions()

    fun getFavoriteInstructions(): Flow<List<Instruction>> = instructionDao.getFavoriteInstructions()

    fun getSections(): Flow<List<String?>> = instructionDao.getSections()

    fun getInstructionsBySection(sectionName: String): Flow<List<Instruction>> =
        instructionDao.getInstructionsBySection(sectionName)

    fun getInstructionById(instructionId: Int): Flow<Instruction?> =
        instructionDao.getInstructionById(instructionId)

    fun searchInstructions(query: String): Flow<List<Instruction>> =
        instructionDao.searchInstructions(query)

    fun searchFavoriteInstructions(query: String): Flow<List<Instruction>> =
        instructionDao.searchFavoriteInstructions(query)

    // Добавляем нужный метод:
    suspend fun deleteSection(sectionName: String) {
        instructionDao.deleteInstructionsBySection(sectionName)
    }
}