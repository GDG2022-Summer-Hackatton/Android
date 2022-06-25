package com.gdg.chicpick.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.model.repository.SurveyResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    private val surveyResultRepository: SurveyResultRepository
) : ViewModel() {
    private val _surveyResult = MutableLiveData<SurveyResult>()
    val surveyResult: LiveData<SurveyResult> get() = _surveyResult

    private val _top3Chicken = MutableLiveData<List<RecommendedChicken>>()
    val top3Chicken : LiveData<List<RecommendedChicken>> get() = _top3Chicken

    fun getResult(userId: Int) = viewModelScope.launch {
        _surveyResult.postValue(surveyResultRepository.getSurveyResult(userId))
        _top3Chicken.postValue(surveyResultRepository.getTop3Chickens(userId))
    }

}