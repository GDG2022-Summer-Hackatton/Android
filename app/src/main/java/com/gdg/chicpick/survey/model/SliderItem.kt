package com.gdg.chicpick.survey.model

data class SliderItem(
    val url: String,
    val text: String,
    val description: String,
    val isChecked: Boolean = false
)