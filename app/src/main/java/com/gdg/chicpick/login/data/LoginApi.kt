package com.gdg.chicpick.login.data

import com.gdg.chicpick.login.data.request.LoginRequest
import com.gdg.chicpick.login.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/member/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}