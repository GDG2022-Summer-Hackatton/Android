package com.gdg.chicpick.survey.network

import com.gdg.chicpick.survey.network.model.RequestSubmitSurvey
import com.gdg.chicpick.survey.network.model.ResponseGetSurvey
import com.gdg.chicpick.survey.network.model.ResponseSubmitSurvey
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SurveyService {
    @POST("/survey")
    suspend fun submitSurvey(
        @Body request: RequestSubmitSurvey
    ) : ResponseSubmitSurvey

    @GET("/survey")
    suspend fun getSurvey(
        @Query("memberId")
        memberId: Int
    ) : ResponseGetSurvey
}