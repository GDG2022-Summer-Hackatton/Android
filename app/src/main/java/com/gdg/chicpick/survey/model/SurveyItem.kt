package com.gdg.chicpick.survey.model

import androidx.annotation.DrawableRes
import com.gdg.chicpick.survey.EMPTY

sealed class SurveyItem {
    abstract val id: Int
    abstract val question: String

    data class Header(
        override val id: Int,
        override val question: String = EMPTY
    ) : SurveyItem()

    data class MustSelect(
        override val id: Int,
        override val question: String
    ) : SurveyItem()

    data class Footer(
        override val id: Int,
        override val question: String = EMPTY
    ) : SurveyItem()

    sealed class SingleSelection : SurveyItem() {
        abstract val selectedButtonType: SelectedButtonType

        enum class SelectedButtonType {
            First, Second, Third, Fourth, Fifth, Sixth, DonTCare, Unknown
        }

        data class TwoButton(
            override val id: Int,
            override val question: String,
            override val selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
        ) : SingleSelection()

        data class ThreeButton(
            override val id: Int,
            override val question: String,
            override val selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
        ) : SingleSelection()

        data class FourButton(
            override val id: Int,
            override val question: String,
            override val selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
            val fourthButtonText: ButtonText,
        ) : SingleSelection()

        data class SixButton(
            override val id: Int,
            override val question: String,
            override val selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
            val fourthButtonText: ButtonText,
            val fifthButtonText: ButtonText,
            val sixButtonText: ButtonText,
        ) : SingleSelection()
    }

    sealed class MultiSelection : SurveyItem() {
        enum class SelectedButtonType {
            First, Second, Third, Fourth, Fifth, Sixth, Seventh, Eighth, DonTCare
        }

        data class MultiButtons(
            override val id: Int,
            override val question: String,
            @DrawableRes
            val resId: Int,
            val buttonTexts: List<ButtonText>,
            val selectedButtonTypes: List<SelectedButtonType> = emptyList()
        ) : MultiSelection() {
            companion object {
                const val BUTTON_COUNT = 8
            }
        }

        data class Slider(
            override val id: Int,
            override val question: String,
            val selectedButtonTypes: List<SelectedButtonType> = emptyList(),
            val sliderContents: List<SliderContent>,
        ) : MultiSelection() {
            companion object {
                const val BUTTON_COUNT = 4
            }
        }
    }
}