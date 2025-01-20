// DataStoreManager.kt
package com.example.sputnik.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sputnik.model.Instruction
import com.example.sputnik.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Определяем dataStore на верхнем уровне
private val Context.dataStore by preferencesDataStore(name = "sputnik_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        val INSTRUCTIONS_KEY = stringPreferencesKey("instructions_key")
        val SECTIONS_KEY = stringPreferencesKey("sections_key")
    }

    // Используем один экземпляр Json с настройками
    private val json = Json {
        ignoreUnknownKeys = true
    }

    // Сохранение инструкций
    suspend fun saveInstructions(instructions: List<Instruction>) {
        try {
            context.dataStore.edit { preferences ->
                val jsonData = json.encodeToString(instructions)
                preferences[INSTRUCTIONS_KEY] = jsonData
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("DataStoreManager", "Ошибка при сохранении инструкций", e)
            // Дополнительные действия при ошибке
        }
    }

    // Получение инструкций
    fun getInstructions(): Flow<List<Instruction>> {
        return context.dataStore.data
            .catch { e ->
                e.printStackTrace()
                Log.e("DataStoreManager", "Ошибка при доступе к данным инструкций", e)
                emit(emptyPreferences())
            }
            .map { preferences ->
                val jsonData = preferences[INSTRUCTIONS_KEY] ?: ""
                if (jsonData.isNotEmpty()) {
                    try {
                        json.decodeFromString<List<Instruction>>(jsonData)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("DataStoreManager", "Ошибка при десериализации инструкций", e)
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }
    }

    // Сохранение секций
    suspend fun saveSections(sections: List<Section>) {
        try {
            context.dataStore.edit { preferences ->
                val jsonData = json.encodeToString(sections)
                preferences[SECTIONS_KEY] = jsonData
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("DataStoreManager", "Ошибка при сохранении секций", e)
            // Дополнительные действия при ошибке
        }
    }

    // Получение секций
    fun getSections(): Flow<List<Section>> {
        return context.dataStore.data
            .catch { e ->
                e.printStackTrace()
                Log.e("DataStoreManager", "Ошибка при доступе к данным секций", e)
                emit(emptyPreferences())
            }
            .map { preferences ->
                val jsonData = preferences[SECTIONS_KEY] ?: ""
                if (jsonData.isNotEmpty()) {
                    try {
                        json.decodeFromString<List<Section>>(jsonData)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("DataStoreManager", "Ошибка при десериализации секций", e)
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            }
    }
}
