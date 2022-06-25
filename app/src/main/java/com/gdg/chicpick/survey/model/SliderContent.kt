package com.gdg.chicpick.survey.model

import androidx.annotation.DrawableRes

data class SliderContent(
    @DrawableRes
    val resId: Int?,
    val title: String,
    val description: String
)