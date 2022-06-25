package com.gdg.chicpick.result.data.api

import com.gdg.chicpick.result.data.response.SurveyResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SurveyResultApi {
    @GET("/survey")
    suspend fun getSurveyResult(@Query("memberId") memberId: Int) : SurveyResultResponse

    @GET("/analysis")
    suspend fun getTop10Chickens(@Query("memberId") memberId: Int) : List<String>
}