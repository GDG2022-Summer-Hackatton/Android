package com.gdg.chicpick.result.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdg.chicpick.databinding.ActivityResultBinding
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.login.view.LoginActivity
import com.gdg.chicpick.result.ResultInstances
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.viewmodel.ResultViewModel
import com.gdg.chicpick.survey.view.SurveyActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ResultViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ResultViewModel(ResultInstances.resultRepository) as T
            }
        }
    }
    private val userId: Int by lazy {
        intent.getIntExtra("userId", -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
        initViewModel()

        viewModel.getResult(userId)
    }

    private fun initView() = with(binding) {
        layoutModifyResult.setOnClickListener {
            startActivity(Intent(this@ResultActivity, SurveyActivity::class.java).apply {
                putExtra(LoginActivity.EXTRA_ID, userId)
            })
            finish()
        }

        textViewShareResult.setOnClickListener {

        }
    }

    private fun initViewModel() = with(viewModel) {
        surveyResult.observe(this@ResultActivity) { surveyResult ->

            binding.textViewResultKeyType.text = generateKeyword(surveyResult)
            binding.textViewResultCommentTitle.text = surveyResult.surveyCommentTitle
            binding.textViewResultCommentDescription.text = surveyResult.surveyCommentDescription
            binding.imageViewResultType.setImageDrawable(
                ContextCompat.getDrawable(this@ResultActivity, surveyResult.chickenType.getImageResource())
            )

            // 추천 치킨 표시
            surveyResult.recommendedChickens.let {
                if (it.isNotEmpty()) {
                    binding.textViewResultChicken1.text = it[0].chickenMenuName
                    binding.textViewResultChickenPercentage1.text =
                        String.format("%.0f%%", it[0].chickenMenuMatch * 100)
                } else {
                    binding.textViewResultChicken1.isVisible = false
                    binding.textViewResultChickenPercentage1.isVisible = false
                }

                if (it.size > 1) {
                    binding.textViewResultChicken2.text = it[1].chickenMenuName
                    binding.textViewResultChickenPercentage2.text =
                        String.format("%.0f%%", it[1].chickenMenuMatch * 100)
                } else {
                    binding.textViewResultChicken2.isVisible = false
                    binding.textViewResultChickenPercentage2.isVisible = false
                }

                if (it.size > 2) {
                    binding.textViewResultChicken3.text = it[2].chickenMenuName
                    binding.textViewResultChickenPercentage3.text =
                        String.format("%.0f%%", it[2].chickenMenuMatch * 100)
                } else {
                    binding.textViewResultChicken3.isVisible = false
                    binding.textViewResultChickenPercentage3.isVisible = false
                }
            }
        }
    }

    private fun generateKeyword(surveyResult: SurveyResult) : String{
        val stringBuilder = StringBuilder()

        stringBuilder.append(surveyResult.chickenType.toKeyword())
        stringBuilder.append("-")
        stringBuilder.append(surveyResult.taste)
        stringBuilder.append(surveyResult.spicy)

        return stringBuilder.toString()
    }
}