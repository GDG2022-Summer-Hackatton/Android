package com.gdg.chicpick.login.data.mapper

import com.gdg.chicpick.login.data.response.LoginResponse
import com.gdg.chicpick.login.model.User

fun LoginResponse.toUser() = User(
    id, email, password, hasSurvey
)