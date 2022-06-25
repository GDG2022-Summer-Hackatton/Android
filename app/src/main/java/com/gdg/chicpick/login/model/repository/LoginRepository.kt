package com.gdg.chicpick.login.model.repository

import com.gdg.chicpick.login.model.User

interface LoginRepository {
    suspend fun login(email: String, password: String): User
}