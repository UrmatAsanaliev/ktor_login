package com.nonmagis.data.repository

import com.nonmagis.data.model.LoginModel
import com.nonmagis.domain.repository.LoginRepository

class LoginRepositoryImpl: LoginRepository {

    private val listOfUsers = arrayListOf<LoginModel>()

    override suspend fun getUsers(): List<LoginModel>? {
        return listOfUsers.toList()
    }

    override suspend fun getUserById(email: String): LoginModel? {
        return listOfUsers.find { it.email == email }
    }

    override suspend fun createUser(model: LoginModel): Boolean {
        // Проверка, есть ли пользователь с таким email
        val exists = listOfUsers.any { it.email == model.email }
        if (exists) return false

        // Добавление нового пользователя
        listOfUsers.add(model)
        return true
    }
}