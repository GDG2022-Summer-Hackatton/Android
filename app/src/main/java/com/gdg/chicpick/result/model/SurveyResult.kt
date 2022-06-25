package com.gdg.chicpick.result.model

data class SurveyResult(
    val surveyCommentTitle: String,
    val surveyCommentDescription: String,
    val chickenType: ChickenType,
    val taste: String,
    val spicy: Int
)
