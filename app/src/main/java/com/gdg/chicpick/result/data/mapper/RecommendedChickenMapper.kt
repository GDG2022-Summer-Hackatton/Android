package com.gdg.chicpick.result.data.mapper

import com.gdg.chicpick.result.data.response.AnalysisResponse
import com.gdg.chicpick.result.model.RecommendedChicken

val AnalysisResponse.top3Chickens: List<RecommendedChicken>
    get() {
        return chickens.map {
            RecommendedChicken(
                it[1] as String, it[2] as String, it[3] as String,
                it[4] as Double
            )
        }.subList(0, 3)
    }