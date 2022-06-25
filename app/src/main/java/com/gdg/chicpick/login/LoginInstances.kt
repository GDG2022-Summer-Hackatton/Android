package com.gdg.chicpick.login

import com.gdg.chicpick.login.data.LoginApi
import com.gdg.chicpick.login.data.repository.LoginRepositoryImpl
import com.gdg.chicpick.login.model.repository.LoginRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginInstances {
    private var _loginApi: LoginApi? = null
    private var _loginRepository: LoginRepository? = null

    val loginApi: LoginApi
        get() {
            return _loginApi ?: Retrofit.Builder()
                .baseUrl("http://43.200.11.44:8080")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoginApi::class.java).also {
                    _loginApi = it
                }
        }

    val loginRepository: LoginRepository
        get() {
            return _loginRepository ?: LoginRepositoryImpl(loginApi)
        }
}