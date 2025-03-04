// AdministratorDao.kt
package com.example.sputnikapp.data.dao

import androidx.room.*
import com.example.sputnikapp.data.entities.Administrator
import kotlinx.coroutines.flow.Flow

@Dao
interface AdministratorDao {
    @Query("SELECT * FROM administrators WHERE login = :login AND password = :password")
    suspend fun authenticate(login: String, password: String): Administrator?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addAdministrator(admin: Administrator)

    @Delete
    suspend fun deleteAdministrator(admin: Administrator)

    @Query("SELECT * FROM administrators")
    fun getAllAdministrators(): Flow<List<Administrator>>

    @Query("SELECT COUNT(*) FROM administrators")
    suspend fun getAdministratorsCount(): Int

    @Query("SELECT * FROM administrators WHERE login LIKE '%' || :query || '%'")
    fun searchAdministrators(query: String): Flow<List<Administrator>>
}