package com.gdg.chicpick.survey.model

import androidx.annotation.DrawableRes
import com.gdg.chicpick.survey.EMPTY

sealed class SurveyItem {
    abstract val id: Int
    abstract val question: String

    abstract fun getCode(): String
    abstract fun setSelectedButtonType(a: String?)

    data class Header(
        override val id: Int,
        override val question: String = EMPTY
    ) : SurveyItem() {
        override fun getCode(): String = "" // header는 코드 불필요.
        override fun setSelectedButtonType(a: String?) {

        }
    }

    data class MustSelect(
        override val id: Int,
        override val question: String,
        val selectedItem: Int = -1
    ) : SurveyItem() {
        override fun getCode(): String {
            return mustSelectItemMap[selectedItem]?.second ?: "q1"
        }

        override fun setSelectedButtonType(a: String?) {

        }

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
    ) : SurveyItem() {
        override fun setSelectedButtonType(a: String?) {

        }

        override fun getCode(): String {
            return ""
        }
    }

    sealed class SingleSelection : SurveyItem() {
        abstract val selectedButtonType: SelectedButtonType

        enum class SelectedButtonType {
            First, Second, Third, Fourth, Fifth, Sixth, DonTCare, Unknown
        }

        data class TwoButton(
            override val id: Int,
            override val question: String,
            override var selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
        ) : SingleSelection() {
            override fun setSelectedButtonType(a: String?) {
                a ?: return

                selectedButtonType = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    else -> SelectedButtonType.DonTCare
                }
            }

            override fun getCode(): String {
                return when(selectedButtonType) {
                    SelectedButtonType.First -> "A1"
                    SelectedButtonType.Second -> "A2"
                    else -> "A3"
                }
            }
        }

        data class ThreeButton(
            override val id: Int,
            override val question: String,
            override var selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
        ) : SingleSelection() {
            override fun setSelectedButtonType(a: String?) {
                a ?: return

                selectedButtonType = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    "A3" -> SelectedButtonType.Third
                    else -> SelectedButtonType.DonTCare
                }
            }

            override fun getCode(): String {
                return when(selectedButtonType) {
                    SelectedButtonType.First -> "A1"
                    SelectedButtonType.Second -> "A2"
                    SelectedButtonType.Third -> "A3"
                    else -> "A4"
                }
            }
        }

        data class FourButton(
            override val id: Int,
            override val question: String,
            override var selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
            val fourthButtonText: ButtonText,
        ) : SingleSelection() {
            override fun setSelectedButtonType(a: String?) {
                a ?: return

                selectedButtonType = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    "A3" -> SelectedButtonType.Third
                    "A4" -> SelectedButtonType.Fourth
                    else -> SelectedButtonType.DonTCare
                }
            }

            override fun getCode(): String {
                return when(selectedButtonType) {
                    SelectedButtonType.First -> "A1"
                    SelectedButtonType.Second -> "A2"
                    SelectedButtonType.Third -> "A3"
                    SelectedButtonType.Fourth -> "A4"
                    else -> "A5"
                }
            }
        }

        data class SixButton(
            override val id: Int,
            override val question: String,
            override var selectedButtonType: SelectedButtonType = SelectedButtonType.Unknown,
            val firstButtonText: ButtonText,
            val secondButtonText: ButtonText,
            val thirdButtonText: ButtonText,
            val fourthButtonText: ButtonText,
            val fifthButtonText: ButtonText,
            val sixButtonText: ButtonText,
        ) : SingleSelection() {
            override fun setSelectedButtonType(a: String?) {
                a ?: return

                selectedButtonType = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    "A3" -> SelectedButtonType.Third
                    "A4" -> SelectedButtonType.Fourth
                    "A5" -> SelectedButtonType.Fifth
                    else -> SelectedButtonType.DonTCare
                }
            }

            override fun getCode(): String {
                return when(selectedButtonType) {
                    SelectedButtonType.First -> "A1"
                    SelectedButtonType.Second -> "A2"
                    SelectedButtonType.Third -> "A3"
                    SelectedButtonType.Fourth -> "A4"
                    SelectedButtonType.Fifth -> "A5"
                    else -> "A6"
                }
            }
        }
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
            var selectedButtonTypes: List<SelectedButtonType> = emptyList()
        ) : MultiSelection() {
            companion object {
                const val BUTTON_COUNT = 8
            }

            override fun getCode(): String {
                return when {
                    SelectedButtonType.First in selectedButtonTypes -> "A1"
                    SelectedButtonType.Second in selectedButtonTypes -> "A2"
                    SelectedButtonType.Third in selectedButtonTypes -> "A3"
                    SelectedButtonType.Fourth in selectedButtonTypes -> "A4"
                    SelectedButtonType.Fifth in selectedButtonTypes -> "A5"
                    SelectedButtonType.Sixth in selectedButtonTypes -> "A6"
                    SelectedButtonType.Seventh in selectedButtonTypes -> "A7"
                    SelectedButtonType.Eighth in selectedButtonTypes -> "A8"
                    else -> "A9"
                }
            }

            override fun setSelectedButtonType(a: String?) {
                a ?: return

                val type = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    "A3" -> SelectedButtonType.Third
                    "A4" -> SelectedButtonType.Fourth
                    "A5" -> SelectedButtonType.Fifth
                    "A6" -> SelectedButtonType.Sixth
                    "A7" -> SelectedButtonType.Seventh
                    "A8" -> SelectedButtonType.Eighth
                    else -> SelectedButtonType.DonTCare
                }
                selectedButtonTypes = listOf(type)
            }
        }

        data class Slider(
            override val id: Int,
            override val question: String,
            var selectedButtonTypes: List<SelectedButtonType> = emptyList(),
            val sliderContents: List<SliderContent>,
        ) : MultiSelection() {
            companion object {
                const val BUTTON_COUNT = 4
            }

            override fun getCode(): String {
                return when {
                    SelectedButtonType.First in selectedButtonTypes -> "A1"
                    SelectedButtonType.Second in selectedButtonTypes -> "A2"
                    SelectedButtonType.Third in selectedButtonTypes -> "A3"
                    else -> "A4"
                }
            }

            override fun setSelectedButtonType(a: String?) {
                a ?: return

                val type = when(a) {
                    "A1" -> SelectedButtonType.First
                    "A2" -> SelectedButtonType.Second
                    "A3" -> SelectedButtonType.Third
                    else -> SelectedButtonType.DonTCare
                }

                selectedButtonTypes = listOf(type)
            }
        }
    }
}