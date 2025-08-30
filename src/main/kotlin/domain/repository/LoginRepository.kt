package com.nonmagis.domain.repository

import com.nonmagis.data.model.LoginModel

interface LoginRepository {

    suspend fun getUsers(): List<LoginModel>?

    suspend fun getUserById(email: String): LoginModel?

    suspend fun createUser(model: LoginModel): Boolean
}