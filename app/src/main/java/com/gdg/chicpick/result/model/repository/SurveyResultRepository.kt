package com.gdg.chicpick.result.model.repository

import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult

interface SurveyResultRepository {
    suspend fun getSurveyResult(
        userId: Int
    ) : SurveyResult

    suspend fun getTop3Chickens(
        userId: Int
    ) : List<RecommendedChicken>
}