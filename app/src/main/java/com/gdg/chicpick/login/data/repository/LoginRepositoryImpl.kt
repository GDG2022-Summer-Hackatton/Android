package com.gdg.chicpick.login.data.repository

import com.gdg.chicpick.login.data.api.LoginApi
import com.gdg.chicpick.login.data.mapper.toUser
import com.gdg.chicpick.login.data.request.LoginRequest
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.login.model.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginApi: LoginApi
) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ) : User {
        return loginApi.login(
            LoginRequest(email, password)
        ).toUser()
    }
}