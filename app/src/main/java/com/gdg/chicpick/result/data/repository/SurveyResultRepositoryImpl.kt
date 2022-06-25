package com.gdg.chicpick.result.data.repository

import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.data.api.SurveyResultApi
import com.gdg.chicpick.result.data.mapper.toSurveyResult
import com.gdg.chicpick.result.data.mapper.top3Chickens
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.model.repository.SurveyResultRepository

class SurveyResultRepositoryImpl(
    private val surveyResultApi: SurveyResultApi
) : SurveyResultRepository{
    override suspend fun getSurveyResult(userId: Int): SurveyResult {
        return surveyResultApi.getSurveyResult(userId).toSurveyResult()
    }

    override suspend fun getTop3Chickens(userId: Int): List<RecommendedChicken> {
        return surveyResultApi.getTop10Chickens(userId).top3Chickens
    }
}