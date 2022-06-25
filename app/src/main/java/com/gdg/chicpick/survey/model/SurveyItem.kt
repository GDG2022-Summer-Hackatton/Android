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
        override val question: String,
        val selectedItem: Int = -1
    ) : SurveyItem() {
        companion object {
            val mustSelectItemMap = linkedMapOf(
                0 to ("기본 치킨 vs 양념 치킨" to "q1"),
                1 to ("뼈 치킨 vs 순살 치킨" to "q2"),
                2 to ("튀긴 치킨 vs 구운 치킨" to "q3"),
                3 to ("선호 부위" to "q4"),
                4 to ("매운 정도" to "q9")
            )
        }
    }

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