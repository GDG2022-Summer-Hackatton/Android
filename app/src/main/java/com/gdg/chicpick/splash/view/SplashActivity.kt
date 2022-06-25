package com.gdg.chicpick.splash.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdg.chicpick.R
import com.gdg.chicpick.login.view.contract.LoginContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val loginActivityContract = registerForActivityResult(LoginContract()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            loginActivityContract.launch(null)
            finish()
        }
    }
}