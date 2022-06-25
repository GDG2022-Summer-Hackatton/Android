package com.gdg.chicpick.result.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdg.chicpick.R
import com.gdg.chicpick.databinding.ActivityResultBinding
import com.gdg.chicpick.login.view.LoginActivity
import com.gdg.chicpick.result.ResultInstances
import com.gdg.chicpick.result.model.RecommendedChicken
import com.gdg.chicpick.result.model.SurveyResult
import com.gdg.chicpick.result.viewmodel.ResultViewModel
import com.gdg.chicpick.survey.view.SurveyActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
                ContextCompat.getDrawable(
                    this@ResultActivity,
                    surveyResult.chickenType.getImageResource()
                )
            )
            binding.textViewShareResult.setOnClickListener {
                shareText(generateKeyword(surveyResult))
            }

        top3Chicken.observe(this@ResultActivity) { chicken ->
            if (chicken.isNotEmpty()) {
                binding.textViewResultChicken1.text = chicken[0].name
                binding.textViewResultChicken1.setOnClickListener {
                    showChickenDetailDialog(chicken[0])
                }
            } else {
                binding.cardViewResultChicken1.isVisible = false
            }

            if (chicken.size > 1) {
                binding.textViewResultChicken2.text = chicken[1].name
                binding.textViewResultChicken2.setOnClickListener {
                    showChickenDetailDialog(chicken[1])
                }
            } else {
                binding.cardViewResultChicken2.isVisible = false
            }

            if (chicken.size > 2) {
                binding.textViewResultChicken3.text = chicken[2].name
                binding.textViewResultChicken3.setOnClickListener {
                    showChickenDetailDialog(chicken[2])
                }
            } else {
                binding.cardViewResultChicken3.isVisible = false
            }
        }
    }

    private fun showChickenDetailDialog(recommendedChicken: RecommendedChicken) {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialogStyle_Rounded)
            .setTitle("${recommendedChicken.brand} ${recommendedChicken.name}")
            .setMessage(recommendedChicken.description)
            .setPositiveButton("닫기") { _, _ -> }
            .show()
    }

    private fun generateKeyword(surveyResult: SurveyResult): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(surveyResult.chickenType.toKeyword())
        stringBuilder.append("-")
        stringBuilder.append(surveyResult.taste)
        stringBuilder.append(surveyResult.spicy)

        return stringBuilder.toString()
    }

    private fun shareText(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
