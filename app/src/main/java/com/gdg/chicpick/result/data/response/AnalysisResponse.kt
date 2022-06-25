package com.gdg.chicpick.result.data.response

data class AnalysisResponse(
    val chickens: List<List<Any>>
)

data class ChickenResponse(
    val brand: String,
    val name: String,
    val description: String,
    val score: Double
)