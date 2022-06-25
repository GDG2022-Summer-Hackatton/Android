package com.gdg.chicpick.result

import com.gdg.chicpick.contant.BASE_URL
import com.gdg.chicpick.login.data.api.LoginApi
import com.gdg.chicpick.login.model.repository.LoginRepository
import com.gdg.chicpick.result.data.api.SurveyResultApi
import com.gdg.chicpick.result.data.repository.SurveyResultRepositoryImpl
import com.gdg.chicpick.result.model.repository.SurveyResultRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ResultInstances {
    private var _surveyResultApi: SurveyResultApi? = null
    private var _resultRepository: SurveyResultRepository? = null

    val surveyResultApi: SurveyResultApi
        get() {
            return _surveyResultApi ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SurveyResultApi::class.java).also {
                    _surveyResultApi = it
                }
        }

    val resultRepository: SurveyResultRepository
        get() {
            //return _resultRepository ?: FakeSurveyResultRepository()
            return _resultRepository ?: SurveyResultRepositoryImpl(surveyResultApi).also { _resultRepository = it }
        }
}