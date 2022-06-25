package com.gdg.chicpick.login.view.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.gdg.chicpick.login.view.LoginActivity

class LoginContract : ActivityResultContract<Void?, Unit>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context, LoginActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}