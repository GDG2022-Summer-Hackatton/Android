package com.gdg.chicpick.result.view.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.result.view.ResultActivity

class ResultActivityContract : ActivityResultContract<Int, Unit>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, ResultActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("userId", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}