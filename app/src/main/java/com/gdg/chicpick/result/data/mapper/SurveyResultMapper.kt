package com.gdg.chicpick.result.data.mapper

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.gdg.chicpick.result.data.response.SurveyResultResponse
import com.gdg.chicpick.result.model.ChickenType
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult
import kotlin.random.Random

fun SurveyResultResponse.toSurveyResult(): SurveyResult {
    val source = q1 == "A2"
    val sunsal = q2 == "A2"
    val roast = q3 == "A2"

    val chickenType = if (source) {
        if (sunsal) {
            if (roast) ChickenType.RSSO
            else ChickenType.FSSO
        } else {
            if (roast) ChickenType.RSO
            else ChickenType.FSO
        }
    } else {
        if (sunsal) {
            if (roast) ChickenType.RS
            else ChickenType.FS
        } else {
            if (roast) ChickenType.R
            else ChickenType.F
        }
    }

    /*
    SurveyResult(
            surveyCommentTitle = "양념, 순살, 구이",
            surveyCommentDescription = "특정한 맛을 강력하게 선호하거나,\n" +
                    "변칙적인 맛을 즐길 줄 알고 있습니다.\n" +
                    "편하게 먹는 것을 즐기고\n" +
                    "구운 치킨 특유의 부드러움,\n" +
                    "촉촉함을 선호하거나\n" +
                    "칼로리를 신경씁니다.",
            chickenType = ChickenType.RSSO,
            recommendedChickens = listOf(
                RecommendedChicken("푸라닭 고추마요 순살", 0.95),
                RecommendedChicken("오빠닭 요거닭", 0.89),
                RecommendedChicken("굽네 볼케이노 순살", 0.78)
            ),
            taste = "맵",
            spicy = 4
        )
     */

    return SurveyResult(
        surveyCommentTitle = when (chickenType) {
            ChickenType.F -> "기본, 뼈, 튀김"
            ChickenType.FS -> "기본, 순살, 튀김"
            ChickenType.FSO -> "양념, 뼈, 튀김"
            ChickenType.FSSO -> "양념, 순살, 튀김"
            ChickenType.R -> "기본, 뼈, 구이"
            ChickenType.RS -> "기본, 순살, 구이"
            ChickenType.RSO -> "양념, 뼈, 구이"
            ChickenType.RSSO -> "양념, 순살, 구이"
        },
        surveyCommentDescription = when (chickenType) {
            ChickenType.F -> "당신은 근본 그 자체입니다.\n치킨 자체의 식감과 미묘한 맛을 즐깁니다.\n부위별로 보다 다채로운 식감을 즐기고 튀긴 치킨 특유의 바삭함을 선호합니다."
            ChickenType.FS -> "혹시 미니멀리스트 이신가요...?!\n치킨 자체의 식감과 미묘한 맛을 즐깁니다.\n편하게 먹는 것을 즐기고 튀긴 치킨 특유의 바삭함을 선호합니다."
            ChickenType.FSO -> "특정한 맛을 강력하게 선호하거나, 변칙적인 맛을 즐길 줄 알고 있습니다.\n부위별로 보다 다채로운 식감을 즐기고 튀긴 치킨 특유의 바삭함을 선호합니다."
            ChickenType.FSSO -> "특정한 맛을 강력하게 선호하거나, 변칙적인 맛을 즐길 줄 알고 있습니다.\n편하게 먹는 것을 즐기고 튀긴 치킨 특유의 바삭함을 선호합니다."
            ChickenType.R -> "치킨 자체의 식감과 미묘한 맛을 즐깁니다.\n부위별로 보다 다채로운 식감을 즐기고 구운 치킨 특유의 부드러움, 촉촉함을 선호하거나 칼로리를 신경씁니다."
            ChickenType.RS -> "치킨 자체의 식감과 미묘한 맛을 즐깁니다.\n편하게 먹는 것을 즐기고 구운 치킨 특유의 부드러움, 촉촉함을 선호하거나 칼로리를 신경씁니다."
            ChickenType.RSO -> "특정한 맛을 강력하게 선호하거나, 변칙적인 맛을 즐길 줄 알고 있습니다.\n부위별로 보다 다채로운 식감을 즐기고 구운 치킨 특유의 부드러움, 촉촉함을 선호하거나 칼로리를 신경씁니다."
            ChickenType.RSSO -> "특정한 맛을 강력하게 선호하거나, 변칙적인 맛을 즐길 줄 알고 있습니다.\n편하게 먹는 것을 즐기고 구운 치킨 특유의 부드러움, 촉촉함을 선호하거나 칼로리를 신경씁니다."
        },
        chickenType = chickenType,
        recommendedChickens = when (chickenType) {
            ChickenType.F -> listOf("BBQ 황금 올리브", "BHC 핫 후라이드", "교촌치킨 레드 콤보")
            ChickenType.FS -> listOf("순수치킨 순살치킨", "또래오래 순살치킨", "맘스터치 후라이드 싸이 순살")
            ChickenType.FSO -> listOf("페리카나 양념치킨", "BHC 맛초킹", "처갓집 슈프림 양념치킨")
            ChickenType.FSSO -> listOf("순수치킨 고마치킨 순살", "KFC 블랙라벨", "네네치킨 오리엔탈 파닭 순살")
            ChickenType.R -> listOf("굽네 고추 바사삭", "오븐마루 오리지널 로스트", "푸라닭 치킨(후라이드)")
            ChickenType.RS -> listOf("푸라닭치킨 순살", "굽네 고추바사삭 순살", "오빠닭 오리지널 로스트 순살")
            ChickenType.RSO -> listOf("푸라닭 투움바 치킨", "BBQ 자메이카 통다리", "지코바 숯불양념치킨")
            ChickenType.RSSO -> listOf("푸라닭 고추마요 순살", "오빠닭 요거닭", "굽네 볼케이노 순살")
        }.mapIndexed { index, s ->
            RecommendedChicken(s, 1 - Random.nextDouble(0.05 * (index + 1), 0.15 * (index + 1)))
        },
        taste = listOf("맵", "단", "짠", "신", "맵")[q8.substring(1..1).toInt()],
        spicy = q9.substring(1..1).toInt()
    )
}