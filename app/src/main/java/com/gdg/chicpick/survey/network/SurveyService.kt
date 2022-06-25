package com.gdg.chicpick.survey.network

import com.gdg.chicpick.survey.network.model.RequestSubmitSurvey
import com.gdg.chicpick.survey.network.model.ResponseSubmitSurvey
import retrofit2.http.Body
import retrofit2.http.POST

interface SurveyService {
    @POST("/survey")
    suspend fun submitSurvey(
        @Body request: RequestSubmitSurvey
    ): ResponseSubmitSurvey
}