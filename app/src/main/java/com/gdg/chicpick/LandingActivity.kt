package com.gdg.chicpick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdg.chicpick.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        textViewNotHaveMbti.setOnClickListener {
            // goto mbti test screen
        }

        textViewAlreadyHaveMbti.setOnClickListener {

        }
    }
}