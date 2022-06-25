package com.gdg.chicpick.result.data.repository

import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.model.ChickenType
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.model.repository.SurveyResultRepository

class FakeSurveyResultRepository : SurveyResultRepository{
    override suspend fun getSurveyResult(userId: Int): SurveyResult {
        return SurveyResult(
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
    }
}