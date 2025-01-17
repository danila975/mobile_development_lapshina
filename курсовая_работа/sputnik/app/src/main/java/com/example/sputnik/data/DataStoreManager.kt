package com.example.sputnik.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "sputnik_preferences")

    companion object {
        val INSTRUCTIONS_KEY = stringPreferencesKey("instructions_key")
        val SECTIONS_KEY = stringPreferencesKey("sections_key")
    }

    // Сохранение инструкций
    suspend fun saveInstructions(instructions: List<Instruction>) {
        context.dataStore.edit { preferences ->
            val jsonData = Json.encodeToString(instructions)
            preferences[INSTRUCTIONS_KEY] = jsonData
        }
    }

    // Получение инструкций
    fun getInstructions(): Flow<List<Instruction>> {
        return context.dataStore.data.map { preferences ->
            val jsonData = preferences[INSTRUCTIONS_KEY] ?: ""
            if (jsonData.isNotEmpty()) {
                Json.decodeFromString(jsonData)
            } else {
                emptyList()
            }
        }
    }

    // Аналогичные методы для секций
    suspend fun saveSections(sections: List<Section>) {
        context.dataStore.edit { preferences ->
            val jsonData = Json.encodeToString(sections)
            preferences[SECTIONS_KEY] = jsonData
        }
    }

    fun getSections(): Flow<List<Section>> {
        return context.dataStore.data.map { preferences ->
            val jsonData = preferences[SECTIONS_KEY] ?: ""
            if (jsonData.isNotEmpty()) {
                Json.decodeFromString(jsonData)
            } else {
                emptyList()
            }
        }
    }
}
