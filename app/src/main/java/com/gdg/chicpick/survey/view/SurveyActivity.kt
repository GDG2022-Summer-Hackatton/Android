package com.gdg.chicpick.survey.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdg.chicpick.databinding.ActivitySurveyBinding
import com.gdg.chicpick.survey.adapter.SurveyAdapter
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.viewmodel.SurveyViewModel

class SurveyActivity : AppCompatActivity(), SurveyAdapter.OnSurveyItemClickListener {
    private val viewBinding by lazy {
        ActivitySurveyBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SurveyViewModel>()

    private val surveyAdapter = SurveyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initData()
        bind()
    }

    private fun initData() {
        viewModel.surveyItems.observe(this) {
            surveyAdapter.submitList(it)
        }
    }

    private fun bind() {
        with(viewBinding) {
            recyclerView.apply {
                adapter = surveyAdapter
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)
            }
        }
    }

    override fun onTwoButtonClick(
        twoButton: SurveyItem.SingleSelection.TwoButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        viewModel.updateTwoButton(twoButton, selectedButtonType)
    }
}