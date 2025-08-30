package com.nonmagis.domain.usecase

import com.nonmagis.data.model.LoginModel
import com.nonmagis.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {

    suspend fun getUsers(): List<LoginModel>? {
        return repository.getUsers()
    }

    suspend fun getUserById(email: String): LoginModel? {
        return repository.getUserById(email)
    }

    suspend fun createUser(model: LoginModel): Boolean {
        return repository.createUser(model)
    }

}