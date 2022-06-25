package com.gdg.chicpick.login.model

data class User(
    val id: Int,
    val email: String,
    val password: String,
    val hasSurvey: Boolean
)
