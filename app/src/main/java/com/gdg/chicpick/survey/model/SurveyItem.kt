package com.gdg.chicpick.survey.model

sealed class SurveyItem {
    abstract val id: Int
    abstract val question: String

    sealed class SingleSelection : SurveyItem() {
        abstract val selectedButtonType: SelectedButtonType

        enum class SelectedButtonType {
            First, Second, DON_T_CARE, UNKNOWN
        }

        data class TwoButton(
            override val id: Int,
            override val question: String,
            override val selectedButtonType: SelectedButtonType = SelectedButtonType.UNKNOWN,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
        ) : SingleSelection()
    }

    sealed class MultiSelection : SurveyItem() {

    }

    data class Slider(
        override val id: Int,
        override val question: String,
        val items: List<SliderItem>
    ) : SurveyItem()
}