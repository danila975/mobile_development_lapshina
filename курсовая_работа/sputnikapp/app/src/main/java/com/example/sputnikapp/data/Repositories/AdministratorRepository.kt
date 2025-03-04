// AdministratorRepository.kt
package com.example.sputnikapp.data.repositories

import com.example.sputnikapp.data.dao.AdministratorDao
import com.example.sputnikapp.data.entities.Administrator

class AdministratorRepository(private val administratorDao: AdministratorDao) {

    suspend fun authenticate(login: String, password: String): Administrator? {
        return administratorDao.authenticate(login, password)
    }

    suspend fun addAdministrator(admin: Administrator) {
        administratorDao.addAdministrator(admin)
    }

    suspend fun deleteAdministrator(admin: Administrator) {
        administratorDao.deleteAdministrator(admin)
    }

    fun getAllAdministrators() = administratorDao.getAllAdministrators()

    suspend fun getAdministratorsCount(): Int {
        return administratorDao.getAdministratorsCount()
    }

    fun searchAdministrators(query: String) = administratorDao.searchAdministrators(query)
}