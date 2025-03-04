// InstructionDao.kt
package com.example.sputnikapp.data.dao

import androidx.room.*
import com.example.sputnikapp.data.entities.Instruction
import kotlinx.coroutines.flow.Flow

@Dao
interface InstructionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInstruction(instruction: Instruction)

    @Update
    suspend fun updateInstruction(instruction: Instruction)

    @Delete
    suspend fun deleteInstruction(instruction: Instruction)

    @Query("SELECT * FROM instructions")
    fun getAllInstructions(): Flow<List<Instruction>>

    @Query("SELECT * FROM instructions WHERE isFavorite = 1")
    fun getFavoriteInstructions(): Flow<List<Instruction>>

    @Query("SELECT * FROM instructions WHERE id = :instructionId")
    fun getInstructionById(instructionId: Int): Flow<Instruction>

    @Query("SELECT * FROM instructions WHERE title LIKE '%' || :query || '%' OR sectionName LIKE '%' || :query || '%'")
    fun searchInstructions(query: String): Flow<List<Instruction>>

    @Query("SELECT * FROM instructions WHERE (title LIKE '%' || :query || '%' OR sectionName LIKE '%' || :query || '%') AND isFavorite = 1")
    fun searchFavoriteInstructions(query: String): Flow<List<Instruction>>

    @Query("SELECT DISTINCT sectionName FROM instructions")
    fun getSections(): Flow<List<String>>

    @Query("SELECT * FROM instructions WHERE sectionName = :sectionName")
    fun getInstructionsBySection(sectionName: String): Flow<List<Instruction>>

    @Query("DELETE FROM instructions WHERE sectionName = :sectionName")
    suspend fun deleteInstructionsBySection(sectionName: String)
}
