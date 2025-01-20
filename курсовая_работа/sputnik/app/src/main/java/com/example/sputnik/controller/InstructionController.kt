// InstructionController.kt
package com.example.sputnik.controller

import android.content.Context
import android.util.Log
import com.example.sputnik.data.DataStoreManager
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InstructionController(context: Context) {
    private val dataStoreManager = DataStoreManager(context)
    private val instructions = MutableStateFlow<List<Instruction>>(emptyList())
    private val sections = MutableStateFlow<List<Section>>(emptyList())
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            try {
                instructions.value = dataStoreManager.getInstructions().first()
                sections.value = dataStoreManager.getSections().first()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("InstructionController", "Ошибка при загрузке данных", e)
                instructions.value = emptyList()
                sections.value = emptyList()
            }
        }
    }

    // Получение всех инструкций
    fun getInstructions(): Flow<List<Instruction>> = instructions
        .catch { e ->
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при получении инструкций", e)
            emit(emptyList())
        }

    // Получение инструкции по ID
    fun getInstructionById(instructionId: Int): Flow<Instruction?> {
        return instructions
            .map { list ->
                list.find { it.id == instructionId }
            }
            .catch { e ->
                e.printStackTrace()
                Log.e("InstructionController", "Ошибка при получении инструкции по ID", e)
                emit(null)
            }
    }

    // Добавление инструкции
    suspend fun addInstruction(instruction: Instruction) {
        try {
            instructions.value = instructions.value + instruction
            dataStoreManager.saveInstructions(instructions.value)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при добавлении инструкции", e)
            // При необходимости можно выполнить откат изменений
        }
    }

    // Добавление в избранное
    suspend fun addToFavorites(instructionId: Int) {
        try {
            val updatedInstructions = instructions.value.map {
                if (it.id == instructionId) it.copy(isFavorite = true) else it
            }
            instructions.value = updatedInstructions
            dataStoreManager.saveInstructions(instructions.value)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при добавлении в избранное", e)
        }
    }

    // Удаление из избранного
    suspend fun removeFromFavorites(instructionId: Int) {
        try {
            val updatedInstructions = instructions.value.map {
                if (it.id == instructionId) it.copy(isFavorite = false) else it
            }
            instructions.value = updatedInstructions
            dataStoreManager.saveInstructions(instructions.value)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при удалении из избранного", e)
        }
    }

    // Получение избранных инструкций
    fun getFavoriteInstructions(): Flow<List<Instruction>> {
        return instructions
            .map { list ->
                list.filter { it.isFavorite }
            }
            .catch { e ->
                e.printStackTrace()
                Log.e("InstructionController", "Ошибка при получении избранных инструкций", e)
                emit(emptyList())
            }
    }

    // Методы для работы с секциями
    fun getSections(): Flow<List<Section>> = sections
        .catch { e ->
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при получении секций", e)
            emit(emptyList())
        }

    suspend fun addSection(section: Section) {
        try {
            sections.value = sections.value + section
            dataStoreManager.saveSections(sections.value)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("InstructionController", "Ошибка при добавлении секции", e)
        }
    }

    // Получение инструкций по секции
    fun getInstructionsBySectionId(sectionId: Int): Flow<List<Instruction>> {
        return instructions
            .map { list ->
                list.filter { it.sectionId == sectionId }
            }
            .catch { e ->
                e.printStackTrace()
                Log.e("InstructionController", "Ошибка при получении инструкций по секции", e)
                emit(emptyList())
            }
    }
}
