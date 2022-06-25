package com.gdg.chicpick.survey.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gdg.chicpick.R
import com.gdg.chicpick.contant.BASE_URL
import com.gdg.chicpick.survey.model.ButtonText
import com.gdg.chicpick.survey.model.SliderContent
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.network.SurveyService
import com.gdg.chicpick.survey.network.model.RequestSubmitSurvey
import com.gdg.chicpick.survey.network.model.ResponseGetSurvey
import com.gdg.chicpick.survey.setValueAfter
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class SurveyViewModel(application: Application) : AndroidViewModel(application) {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val surveyApi = retrofit.create(SurveyService::class.java)

    private val _addRespSuccess = MutableLiveData<Boolean>()
    val addRespSuccess : LiveData<Boolean> get() = _addRespSuccess

    fun getSurvey(userId: Int) {
        viewModelScope.launch {
            try {
                val resp = surveyApi.getSurvey(userId)
                _surveyItems.value = createSurvey(resp)
            } catch (e: Exception) {
                println("SurveyViewModel getSurvey exception :$e")
                _surveyItems.value = createSurvey(null)
            }
        }
    }

    fun submitSurvey(userId: Int) {
        val codes = mutableListOf<String>()

        surveyItems.value?.let { list ->
            list.forEach {
                if (it !is SurveyItem.Header) { // 헤더가 아닌 경우 코드 추출.
                    codes.add(it.getCode())
                }
            }
        }

        val request = RequestSubmitSurvey(
            memberId = userId,
            q1 = codes[0],
            q2 = codes[1],
            q3 = codes[2],
            q4 = codes[3],
            q5 = codes[4],
            q6 = codes[5],
            q7 = codes[6],
            q8 = codes[7],
            q9 = codes[8],
            q10 = codes[9],
            q11 = codes[10],
            q12 = codes[11],
        )
        println("SurveyViewModel submitSurvey req :$request")
        viewModelScope.launch {
            try {
                surveyApi.submitSurvey(request)
                _addRespSuccess.value = true
            } catch (e: java.io.EOFException) {
                _addRespSuccess.value = true
            } catch (e: Exception) {
                println("SurveyViewModel submitSurvey exception :$e")
            }
        }
    }

    private val _surveyItems = MutableLiveData<List<SurveyItem>>()

    val surveyItems: LiveData<List<SurveyItem>> get() = _surveyItems

    private fun createSurvey(resp: ResponseGetSurvey?): List<SurveyItem> {
        return listOf(
            SurveyItem.Header(id = 0),
            SurveyItem.SingleSelection.TwoButton(
                id = 1,
                question = "Q1 / 12 - 근본 vs 변칙",
                firstButtonText = ButtonText(
                    title = "기본 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "양념 치킨"
                ),
            ).apply {
                    setSelectedButtonType(resp?.q1)
            },
            SurveyItem.SingleSelection.TwoButton(
                id = 2,
                question = "Q2 / 12 - 근본 vs 편리",
                firstButtonText = ButtonText(
                    title = "뼈 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "순살 치킨"
                )
            ).apply {
                setSelectedButtonType(resp?.q2)
            },
            SurveyItem.SingleSelection.TwoButton(
                id = 3,
                question = "Q3 / 12 - 조리 방식",
                firstButtonText = ButtonText(
                    title = "튀긴 치킨"
                ),
                secondButtonText = ButtonText(
                    title = "구운 치킨"
                )
            ).apply {
                setSelectedButtonType(resp?.q3)
            },
            SurveyItem.MultiSelection.MultiButtons(
                id = 4,
                question = "Q4 / 12 - 선호 부위 (중복 가능)",
                resId = R.drawable.chicken_part_380px,
                buttonTexts = listOf(
                    ButtonText("닭 날개", "쫄깃함"),
                    ButtonText("닭 봉", "부르러움"),
                    ButtonText("닭 다리", "쫄깃함"),
                    ButtonText("넓적다리", "부드러움"),
                    ButtonText("닭 가슴살", "퍽퍽함"),
                    ButtonText("닭 안심살", "부드러움"),
                    ButtonText("닭 엉치", "쫄깃함+부드러움+퍽퍽함"),
                    ButtonText("통닭", "모든 부위를 한 번에"),
                )
            ).apply {
                setSelectedButtonType(resp?.q4)
            },
            SurveyItem.MultiSelection.Slider(
                id = 5,
                question = "Q5 / 12 - 튀김 방식",
                sliderContents = listOf(
                    SliderContent(
                        resId = R.drawable.q5_chicken1,
                        title = "튀김 1세대 (엠보싱 표면)",
                        description = "건식 파우더를 통해 입힌 얇은 튀김옷을 가지며 \n" +
                                "튀김옷에 따라 각종 향이 나는 특징이 있는 방식"
                    ),
                    SliderContent(
                        resId = R.drawable.q5_chicken2,
                        title = "튀김 2세대 (밋밋한 표면)",
                        description = "습식 파우더를 통해 만들어진 밋밋한 튀김옷을 특징으로 촉촉한 식감을 제공하는 방식"
                    ),
                    SliderContent(
                        resId = R.drawable.q5_chicken3,
                        title = "튀김 3세대 (크리스피 표면)",
                        description = "건식과 습식 파우더를 혼용하여 바삭한 식감을 극대화한 현재 가장 많이 보이는 방식"
                    ),
                    SliderContent(
                        resId = null,
                        title = "상관없음.",
                        description = "튀김 방식, 아무래도 상관 없어요!"
                    )
                )
            ).apply {
                setSelectedButtonType(resp?.q5)
            },
            SurveyItem.MultiSelection.Slider(
                id = 6,
                question = "Q6 / 12 - 구이 방식",
                sliderContents = listOf(
                    SliderContent(
                        resId = R.drawable.q6_chicken1,
                        title = "로스트",
                        description = "오븐에서 육류, 가금류, 감자 등을 구워내는\n" +
                                "건식열 조리 방식"
                    ),
                    SliderContent(
                        resId = R.drawable.q6_chicken2,
                        title = "베이크",
                        description = "오븐에 넣고 350°F~450°F 정도의 건열로서 조리하는 방식"
                    ),
                    SliderContent(
                        resId = R.drawable.q5_chicken3,
                        title = "훈제",
                        description = "소금에 절인 수조육류를 훈연을 하여 건조시키는 가공 방식"
                    ),
                    SliderContent(
                        resId = null,
                        title = "상관없음.",
                        description = "구이 방식, 아무래도 상관 없어요!"
                    )
                )
            ).apply {
                setSelectedButtonType(resp?.q6)
            },
            SurveyItem.SingleSelection.TwoButton(
                id = 7,
                question = "Q7 / 12 - 양념 방식",
                firstButtonText = ButtonText(
                    title = "부어 먹기",
                    description = "부드러운 식감\n" +
                            "충분히 스며든 양념 선호"
                ),
                secondButtonText = ButtonText(
                    title = "찍어 먹기",
                    description = "바삭한 식감\n" +
                            "조절 가능한 양념 선호"
                )
            ).apply {
                setSelectedButtonType(resp?.q7)
            },
            SurveyItem.SingleSelection.FourButton(
                id = 8,
                question = "Q8 / 12 - 핵심 맛",
                firstButtonText = ButtonText(
                    title = "매운"
                ),
                secondButtonText = ButtonText(
                    title = "단"
                ),
                thirdButtonText = ButtonText(
                    title = "짠"
                ),
                fourthButtonText = ButtonText(
                    title = "신"
                ),
            ).apply {
                setSelectedButtonType(resp?.q8)
            },
            SurveyItem.SingleSelection.SixButton(
                id = 9,
                question = "Q9 / 12 - 매운맛 0~6단계",
                firstButtonText = ButtonText(
                    title = "Lv.1",
                    description = "매운맛 싫어요"
                ),
                secondButtonText = ButtonText(
                    title = "Lv.2",
                    description = "삼양라면, 진라면 순한맛"
                ),
                thirdButtonText = ButtonText(
                    title = "Lv.3",
                    description = "신라면, 엽떡 착한맛"
                ),
                fourthButtonText = ButtonText(
                    title = "Lv.4",
                    description = "불닭볶음면, 엽떡 초보맛"
                ),
                fifthButtonText = ButtonText(
                    title = "Lv.5",
                    description = "핵불닭컵, 엽떡 덜 매운맛"
                ),
                sixButtonText = ButtonText(
                    title = "Lv.6",
                    description = "맛 > 양(조화)"
                )
            ).apply {
                setSelectedButtonType(resp?.q9)
            },
            SurveyItem.SingleSelection.ThreeButton(
                id = 10,
                question = "Q10 / 12 - 비용 vs 맛",
                firstButtonText = ButtonText(
                    title = "가성비",
                    description = "맛 < 양(조화)",
                ),
                secondButtonText = ButtonText(
                    title = "가심비",
                    description = "맛 > 양(조화)",
                ),
                thirdButtonText = ButtonText(
                    title = "미식",
                    description = "오로지 맛.",
                )
            ).apply {
                setSelectedButtonType(resp?.q10)
            },
            SurveyItem.SingleSelection.TwoButton(
                id = 11,
                question = "Q11 / 12 - 냉동 여부",
                firstButtonText = ButtonText(
                    title = "냉장",
                    description = "값 육즙 육질",
                ),
                secondButtonText = ButtonText(
                    title = "냉동",
                    description = "값 육즙 육질",
                )
            ).apply {
                setSelectedButtonType(resp?.q11)
            },
            SurveyItem.MustSelect(
                id = 12,
                question = "Q12 / 12 - 치킨을 선택할 때 이것만은 꼭!"
            ).apply {
                setSelectedButtonType(resp?.q12)
            },
            SurveyItem.Footer(
                id = 13
            )
        )
    }

    fun updateTwoButton(
        twoButton: SurveyItem.SingleSelection.TwoButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(twoButton)

                if (index > -1) {
                    set(index, twoButton.copy(selectedButtonType = selectedButtonType))
                }
            }
        }
    }

    fun updateThreeButton(
        threeButton: SurveyItem.SingleSelection.ThreeButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(threeButton)

                if (index > -1) {
                    set(index, threeButton.copy(selectedButtonType = selectedButtonType))
                }
            }
        }
    }

    fun updateFourButton(
        fourButton: SurveyItem.SingleSelection.FourButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(fourButton)

                if (index > -1) {
                    set(index, fourButton.copy(selectedButtonType = selectedButtonType))
                }
            }
        }
    }

    fun updateSixButton(
        sixButton: SurveyItem.SingleSelection.SixButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(sixButton)

                if (index > -1) {
                    set(index, sixButton.copy(selectedButtonType = selectedButtonType))
                }
            }
        }
    }

    fun updateMultiButtons(
        multiButtons: SurveyItem.MultiSelection.MultiButtons,
        selectedButtonType: SurveyItem.MultiSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(multiButtons)
                val mutableList = multiButtons.selectedButtonTypes.toMutableList()

                mutableList.clear()
                mutableList.add(selectedButtonType)

                if (index > -1) {
                    set(
                        index,
                        multiButtons.copy(
                            selectedButtonTypes = mutableList
                        )
                    )
                }
            }
        }
    }

    fun updateMustSelect(key: Int) {
        _surveyItems.setValueAfter {
            val mustSelect = filterIsInstance<SurveyItem.MustSelect>().firstOrNull()

            if (mustSelect == null) {
                this
            } else {
                val index = indexOf(mustSelect)
                toMutableList().apply {
                    set(index, mustSelect.copy(selectedItem = key))
                }
            }
        }
    }

    fun updateSlider(
        slider: SurveyItem.MultiSelection.Slider,
        selectedButtonType: SurveyItem.MultiSelection.SelectedButtonType
    ) {
        _surveyItems.setValueAfter {
            toMutableList().apply {
                val index = indexOf(slider)
                val mutableList = slider.selectedButtonTypes.toMutableList()

                mutableList.clear()
                mutableList.add(selectedButtonType)

                if (index > -1) {
                    set(
                        index,
                        slider.copy(
                            selectedButtonTypes = mutableList
                        )
                    )
                }
            }
        }
    }
}