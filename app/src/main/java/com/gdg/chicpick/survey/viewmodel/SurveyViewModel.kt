package com.gdg.chicpick.survey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gdg.chicpick.survey.model.ButtonText
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.setValueAfter

class SurveyViewModel(application: Application) : AndroidViewModel(application) {
    private val _surveyItems = MutableLiveData<List<SurveyItem>>(
        listOf(
            SurveyItem.SingleSelection.TwoButton(
                id = 0,
                question = "Q1 / 14 - 근본 vs 변칙",
                firstButtonText = ButtonText(
                    title = "기본 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "양념 치킨"
                )
            ),
            SurveyItem.SingleSelection.TwoButton(
                id = 1,
                question = "Q2 / 14 - 근본 vs 편리",
                firstButtonText = ButtonText(
                    title = "뼈 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "순살 치킨"
                )
            ),
            SurveyItem.SingleSelection.TwoButton(
                id = 2,
                question = "Q3 / 14 - 조리 방식",
                firstButtonText = ButtonText(
                    title = "튀긴 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "구운 치킨"
                )
            )
        )
    )

    val surveyItems: LiveData<List<SurveyItem>> get() = _surveyItems

    fun updateTwoButton(
        twoButton: SurveyItem.SingleSelection.TwoButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(twoButton)

                set(index, twoButton.copy(selectedButtonType = selectedButtonType))
            }
        }
    }
}