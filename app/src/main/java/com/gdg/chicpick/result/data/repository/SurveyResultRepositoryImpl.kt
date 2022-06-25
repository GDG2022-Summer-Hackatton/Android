package com.gdg.chicpick.result.data.repository

import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.data.api.SurveyResultApi
import com.gdg.chicpick.result.data.mapper.toSurveyResult
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.model.repository.SurveyResultRepository

class SurveyResultRepositoryImpl(
    private val surveyResultApi: SurveyResultApi
) : SurveyResultRepository{
    override suspend fun getSurveyResult(user: User): SurveyResult {
        return surveyResultApi.getSurveyResult(user.id).toSurveyResult()
    }
}