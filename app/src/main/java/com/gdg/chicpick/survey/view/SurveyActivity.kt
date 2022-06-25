package com.gdg.chicpick.survey.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gdg.chicpick.R
import com.gdg.chicpick.databinding.ActivitySurveyBinding
import com.gdg.chicpick.login.model.User
import com.gdg.chicpick.login.view.LoginActivity.Companion.EXTRA_ID
import com.gdg.chicpick.result.view.contract.ResultActivityContract
import com.gdg.chicpick.survey.adapter.SurveyAdapter
import com.gdg.chicpick.survey.adapter.SurveyItemDecorator
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.viewmodel.SurveyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SurveyActivity : AppCompatActivity(), SurveyAdapter.OnSurveyItemClickListener {
    private val viewBinding by lazy {
        ActivitySurveyBinding.inflate(layoutInflater)
    }

    private val resultActivityContract = registerForActivityResult(ResultActivityContract()) {}

    private val viewModel by viewModels<SurveyViewModel>()

    private val surveyAdapter = SurveyAdapter(this)

    private val userId by lazy {
        intent?.getIntExtra(EXTRA_ID, 1) ?: 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initData()
        bind()
    }

    private fun initData() {
        viewModel.getSurvey(userId)

        viewModel.surveyItems.observe(this) {
            surveyAdapter.submitList(it)
        }

        viewModel.addRespSuccess.observe(this) {
            if (it == true) {
                resultActivityContract.launch(userId) // 내부적으로 userId만 쓰므로. 이렇게 씁시다.
                finish()
            }
        }
    }

    private fun bind() {
        with(viewBinding) {
            recyclerView.apply {
                adapter = surveyAdapter
                layoutManager = LinearLayoutManager(context)

                addItemDecoration(SurveyItemDecorator())
                setHasFixedSize(true)

                itemAnimator?.let {
                    if (it is SimpleItemAnimator) {
                        it.supportsChangeAnimations = false
                    }
                }
            }
        }
    }

    override fun onTwoButtonClick(
        twoButton: SurveyItem.SingleSelection.TwoButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        viewModel.updateTwoButton(twoButton, selectedButtonType)
    }

    override fun onThreeButtonClick(
        threeButton: SurveyItem.SingleSelection.ThreeButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        viewModel.updateThreeButton(threeButton, selectedButtonType)
    }

    override fun onFourthButtonClick(
        fourButton: SurveyItem.SingleSelection.FourButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        viewModel.updateFourButton(fourButton, selectedButtonType)
    }

    override fun onSixButtonClick(
        sixButton: SurveyItem.SingleSelection.SixButton,
        selectedButtonType: SurveyItem.SingleSelection.SelectedButtonType
    ) {
        viewModel.updateSixButton(sixButton, selectedButtonType)
    }

    override fun onMultiButtonsClick(
        multiButtons: SurveyItem.MultiSelection.MultiButtons,
        clickedButtonType: SurveyItem.MultiSelection.SelectedButtonType
    ) {
        viewModel.updateMultiButtons(multiButtons, clickedButtonType)
    }

    override fun onSliderClick(
        slider: SurveyItem.MultiSelection.Slider,
        clickedButtonType: SurveyItem.MultiSelection.SelectedButtonType
    ) {
        viewModel.updateSlider(slider, clickedButtonType)
    }

    override fun onMustSelectClick(item: SurveyItem.MustSelect) {
        val items = SurveyItem.MustSelect.mustSelectItemMap.values.map { it.first }.toTypedArray()
        MaterialAlertDialogBuilder(this, R.style.MyAlertDialog)
            .setSingleChoiceItems(items, item.selectedItem) { dialog, which ->
                viewModel.updateMustSelect(which)
                dialog.dismiss()
            }
            .show()
    }

    override fun onSubmitClick() {
        viewModel.submitSurvey(userId)
    }
}