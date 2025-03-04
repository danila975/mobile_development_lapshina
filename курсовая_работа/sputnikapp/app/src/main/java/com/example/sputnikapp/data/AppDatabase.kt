package com.example.sputnikapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sputnikapp.data.dao.AdministratorDao
import com.example.sputnikapp.data.dao.InstructionDao
import com.example.sputnikapp.data.entities.Administrator
import com.example.sputnikapp.data.entities.Instruction
import com.example.sputnikapp.data.entities.Section
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Administrator::class, Instruction::class, Section::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun administratorDao(): AdministratorDao
    abstract fun instructionDao(): InstructionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sputnik_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Получаем экземпляр базы данных
                            val database = INSTANCE
                            database?.let {
                                val adminDao = it.administratorDao()
                                // Запускаем корутину для выполнения асинхронных операций
                                CoroutineScope(Dispatchers.IO).launch {
                                    val adminCount = adminDao.getAdministratorsCount()
                                    if (adminCount == 0) {
                                        val admin = Administrator(login = "admin", password = "admin")
                                        adminDao.addAdministrator(admin)
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}