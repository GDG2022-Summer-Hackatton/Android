package com.gdg.chicpick.result

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gdg.chicpick.databinding.ActivityResultBinding
import com.gdg.chicpick.result.viewmodel.ResultViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViewModel()

        viewModel.getResult()
    }

    private fun initViewModel() = with(viewModel) {
        top3ChickenMenu.observe(this@ResultActivity) {
            if (it.isNotEmpty()) {
                binding.textViewResultChicken1.text = it[0].chickenMenuName
                binding.textViewResultChickenPercentage1.text =
                    String.format("%.0f%%", it[0].chickenMenuMatch * 100)
            }
            if (it.size > 1) {
                binding.textViewResultChicken2.text = it[1].chickenMenuName
                binding.textViewResultChickenPercentage2.text =
                    String.format("%.0f%%", it[1].chickenMenuMatch * 100)
            }
            if (it.size > 2) {
                binding.textViewResultChicken3.text = it[2].chickenMenuName
                binding.textViewResultChickenPercentage3.text =
                    String.format("%.0f%%", it[2].chickenMenuMatch * 100)
            }
        }
    }
}