package com.gdg.chicpick.result.view.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.view.ResultActivity

class ResultActivityContract : ActivityResultContract<User, Unit>() {
    override fun createIntent(context: Context, input: User): Intent {
        return Intent(context, ResultActivity::class.java).apply {
            putExtra("userID", input.id)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}