package com.gdg.chicpick.survey.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gdg.chicpick.R
import com.gdg.chicpick.databinding.*
import com.gdg.chicpick.survey.gone
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.model.SurveyItem.MultiSelection
import com.gdg.chicpick.survey.model.SurveyItem.SingleSelection.SelectedButtonType
import com.gdg.chicpick.survey.model.SurveyItem.SingleSelection.TwoButton
import com.gdg.chicpick.survey.visible

class SurveyAdapter(private val onSurveyItemClickListener: OnSurveyItemClickListener) : ListAdapter<SurveyItem, SurveyAdapter.ViewHolder>(DiffCallback()) {
    interface OnSurveyItemClickListener {
        fun onTwoButtonClick(
            twoButton: TwoButton,
            selectedButtonType: SelectedButtonType
        )

        fun onThreeButtonClick(
            threeButton: SurveyItem.SingleSelection.ThreeButton,
            selectedButtonType: SelectedButtonType
        )

        fun onFourthButtonClick(
            fourButton: SurveyItem.SingleSelection.FourButton,
            selectedButtonType: SelectedButtonType
        )

        fun onSixButtonClick(
            sixButton: SurveyItem.SingleSelection.SixButton,
            selectedButtonType: SelectedButtonType
        )

        fun onMultiButtonsClick(
            multiButtons: MultiSelection.MultiButtons,
            clickedButtonType: MultiSelection.SelectedButtonType
        )

        fun onSliderClick(
            slider: MultiSelection.Slider,
            clickedButtonType: MultiSelection.SelectedButtonType
        )

        fun onMustSelectClick(item: SurveyItem.MustSelect)

        fun onSubmitClick()
    }

    sealed class ViewHolder(private val viewBinding: ViewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        abstract fun bind(item: SurveyItem)

        private val context: Context get() = viewBinding.root.context

        @get:ColorInt
        protected val selectedButtonColor: Int get() = context.getColor(R.color.selected)

        @get:ColorInt
        protected val unselectedButtonColor: Int get() = context.getColor(R.color.unselected)

        @get:ColorInt
        protected val donTCareUnselectedButtonColor: Int get() = context.getColor(R.color.don_t_care_unselected)
    }

    inner class SurveyHeaderViewHolder(viewBinding: SurveyHeaderBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) = Unit
    }

    inner class SurveyTwoButtonViewHolder(private val viewBinding: SurveyTwoButtonsBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is TwoButton) {
                with(viewBinding) {
                    textViewQuestion.text = item.question

                    with(buttonPair) {
                        buttonFirst.text = item.firstButtonText.title
                        buttonSecond.text = item.secondButtonText.title

                        item.firstButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.secondButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onTwoButtonClick(
                                item,
                                SelectedButtonType.First
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onTwoButtonClick(
                                item,
                                SelectedButtonType.Second
                            )
                        }

                        buttonDonTCare.setOnClickListener {
                            onSurveyItemClickListener.onTwoButtonClick(
                                item,
                                SelectedButtonType.DonTCare
                            )
                        }
                    }

                    updateUiBySelectedButtonType(item.selectedButtonType)
                }
            }
        }

        private fun updateUiBySelectedButtonType(selectedButtonType: SelectedButtonType) {
            with(viewBinding) {
                when(selectedButtonType) {
                    SelectedButtonType.First -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.Second -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.DonTCare -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                    }
                    else -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                }
            }
        }
    }

    inner class SurveyThreeButtonViewHolder(private val viewBinding: SurveyThreeButtonsBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.SingleSelection.ThreeButton) {
                with(viewBinding) {
                    textViewQuestion.text = item.question

                    with(buttonPair) {
                        buttonFirst.text = item.firstButtonText.title
                        buttonSecond.text = item.secondButtonText.title
                        buttonThird.text = item.thirdButtonText.title

                        item.firstButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.secondButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        item.thirdButtonText.description?.let {
                            textViewDescription3.visible()
                            textViewDescription3.text = it
                        } ?: textViewDescription3.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onThreeButtonClick(
                                item,
                                SelectedButtonType.First
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onThreeButtonClick(
                                item,
                                SelectedButtonType.Second
                            )
                        }

                        buttonThird.setOnClickListener {
                            onSurveyItemClickListener.onThreeButtonClick(
                                item,
                                SelectedButtonType.Third
                            )
                        }

                        buttonDonTCare.setOnClickListener {
                            onSurveyItemClickListener.onThreeButtonClick(
                                item,
                                SelectedButtonType.DonTCare
                            )
                        }
                    }

                    updateUiBySelectedButtonType(item.selectedButtonType)
                }
            }
        }

        private fun updateUiBySelectedButtonType(selectedButtonType: SelectedButtonType) {
            with(viewBinding) {
                when(selectedButtonType) {
                    SelectedButtonType.First -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(arrayOf(buttonPair.buttonSecond, buttonThird), unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.Second -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        buttonThird.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.Third -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonThird.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.DonTCare -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonThird.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                    }
                    else -> {
                        buttonPair.buttonFirst.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonPair.buttonSecond.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonThird.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                }
            }
        }
    }

    inner class SurveyFourButtonViewHolder(private val viewBinding: SurveyFourButtonsBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.SingleSelection.FourButton) {
                with(viewBinding) {
                    textViewQuestion.text = item.question

                    with(buttonPair1) {
                        buttonFirst.text = item.firstButtonText.title
                        buttonSecond.text = item.secondButtonText.title

                        item.firstButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.secondButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onFourthButtonClick(
                                item,
                                SelectedButtonType.First
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onFourthButtonClick(
                                item,
                                SelectedButtonType.Second
                            )
                        }
                    }

                    with(buttonPair2) {
                        buttonFirst.text = item.thirdButtonText.title
                        buttonSecond.text = item.fourthButtonText.title

                        item.thirdButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.fourthButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onFourthButtonClick(
                                item,
                                SelectedButtonType.Third
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onFourthButtonClick(
                                item,
                                SelectedButtonType.Fourth
                            )
                        }
                    }

                    buttonDonTCare.setOnClickListener {
                        onSurveyItemClickListener.onFourthButtonClick(
                            item,
                            SelectedButtonType.DonTCare
                        )
                    }

                    updateUiBySelectedButtonType(item.selectedButtonType)
                }
            }
        }

        private fun updateUiBySelectedButtonType(selectedButtonType: SelectedButtonType) {
            with(viewBinding) {
                buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)

                when(selectedButtonType) {
                    SelectedButtonType.First -> {
                        buttonPair1.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(buttonPair1.buttonSecond, buttonPair2.buttonFirst, buttonPair2.buttonSecond),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Second -> {
                        buttonPair1.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(buttonPair1.buttonFirst, buttonPair2.buttonFirst, buttonPair2.buttonSecond),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Third -> {
                        buttonPair2.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(buttonPair1.buttonFirst, buttonPair1.buttonSecond, buttonPair2.buttonSecond),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Fourth -> {
                        buttonPair2.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(buttonPair1.buttonFirst, buttonPair1.buttonSecond, buttonPair2.buttonFirst),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.DonTCare -> {
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonFirst, buttonPair2.buttonSecond,
                                buttonPair1.buttonSecond, buttonPair2.buttonFirst
                            ),
                            unselectedButtonColor
                        )
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                    }
                    else -> {
                        setBackgroundTintList(
                            arrayOf(buttonPair1.buttonFirst, buttonPair1.buttonSecond, buttonPair2.buttonFirst),
                            unselectedButtonColor
                        )
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                }
            }
        }
    }

    inner class SurveySixButtonViewHolder(private val viewBinding: SurveySixButtonsBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.SingleSelection.SixButton) {
                with(viewBinding) {
                    textViewQuestion.text = item.question

                    with(buttonPair1) {
                        buttonFirst.text = item.firstButtonText.title
                        buttonSecond.text = item.secondButtonText.title

                        item.firstButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.secondButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.First
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.Second
                            )
                        }
                    }

                    with(buttonPair2) {
                        buttonFirst.text = item.thirdButtonText.title
                        buttonSecond.text = item.fourthButtonText.title

                        item.thirdButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

                        item.fourthButtonText.description?.let {
                            textViewDescription2.visible()
                            textViewDescription2.text = it
                        } ?: textViewDescription2.gone()

                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.Third
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.Fourth
                            )
                        }
                    }

                    with(buttonPair3) {
                        // 버튼 5개 표시.
                        buttonSecond.gone()
                        textViewDescription2.gone()

                        buttonFirst.text = item.fifthButtonText.title
                        buttonSecond.text = item.sixButtonText.title

                        item.fifthButtonText.description?.let {
                            textViewDescription1.visible()
                            textViewDescription1.text = it
                        } ?: textViewDescription1.gone()

//                        item.sixButtonText.description?.let {
//                            textViewDescription2.visible()
//                            textViewDescription2.text = it
//                        } ?: textViewDescription2.gone()



                        buttonFirst.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.Fifth
                            )
                        }

                        buttonSecond.setOnClickListener {
                            onSurveyItemClickListener.onSixButtonClick(
                                item,
                                SelectedButtonType.Sixth
                            )
                        }
                    }

                    buttonDonTCare.setOnClickListener {
                        onSurveyItemClickListener.onSixButtonClick(
                            item,
                            SelectedButtonType.DonTCare
                        )
                    }

                    updateUiBySelectedButtonType(item.selectedButtonType)
                }
            }
        }

        private fun updateUiBySelectedButtonType(selectedButtonType: SelectedButtonType) {
            with(viewBinding) {
                buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)

                when(selectedButtonType) {
                    SelectedButtonType.First -> {
                        buttonPair1.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Second -> {
                        buttonPair1.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonFirst,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Third -> {
                        buttonPair2.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonSecond,
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonSecond,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Fourth -> {
                        buttonPair2.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonSecond,
                                buttonPair1.buttonFirst,
                                buttonPair2.buttonFirst,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Fifth -> {
                        buttonPair3.buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair1.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.Sixth -> {
                        buttonPair3.buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair1.buttonFirst,
                                buttonPair3.buttonFirst
                            ),
                            unselectedButtonColor
                        )
                    }
                    SelectedButtonType.DonTCare -> {
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonFirst,
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                    }
                    else -> {
                        setBackgroundTintList(
                            arrayOf(
                                buttonPair1.buttonFirst,
                                buttonPair1.buttonSecond,
                                buttonPair2.buttonFirst,
                                buttonPair2.buttonSecond,
                                buttonPair3.buttonFirst,
                                buttonPair3.buttonSecond
                            ),
                            unselectedButtonColor
                        )
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }
                }
            }
        }
    }

    inner class SurveySliderViewHolder(private val viewBinding: SurveySliderBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is MultiSelection.Slider) {
                with(viewBinding) {
                    textViewQuestion.text = item.question

                    item.sliderContents.forEachIndexed { index, sliderContent ->
                        getSliderItemBindingByIndex(index)?.let { binding ->
                            sliderContent.resId?.let {
                                binding.imageView.setImageResource(it)
                            }

                            binding.textViewTitle.text = sliderContent.title
                            binding.textViewDescription.text = sliderContent.description

                            val type = getSelectedButtonTypeByIndex(index)

                            binding.root.setOnClickListener {
                                onSurveyItemClickListener.onSliderClick(item, type)
                            }

                            updateUiByType(item.selectedButtonTypes)
                        }
                    }
                }
            }
        }

        private fun getSliderItemBindingByIndex(index: Int) = when(index) {
            0 -> viewBinding.surveySliderItem1
            1 -> viewBinding.surveySliderItem2
            2 -> viewBinding.surveySliderItem3
            3 -> viewBinding.surveySliderItem4
            else -> null
        }

        private fun getSelectedButtonTypeByIndex(index: Int) = when(index) {
            0 -> MultiSelection.SelectedButtonType.First
            1 -> MultiSelection.SelectedButtonType.Second
            2 -> MultiSelection.SelectedButtonType.Third
            else -> MultiSelection.SelectedButtonType.DonTCare
        }

        private fun getSliderItemBindingByType(type: MultiSelection.SelectedButtonType) = when(type) {
            MultiSelection.SelectedButtonType.First -> viewBinding.surveySliderItem1
            MultiSelection.SelectedButtonType.Second -> viewBinding.surveySliderItem2
            MultiSelection.SelectedButtonType.Third -> viewBinding.surveySliderItem3
            else -> viewBinding.surveySliderItem4
        }

        private fun updateUiByType(types: List<MultiSelection.SelectedButtonType>) {
            repeat(MultiSelection.Slider.BUTTON_COUNT) { index ->
                getSliderItemBindingByIndex(index)?.let { binding ->
                    binding.root.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                }
            }

            types.forEach { type ->
                getSliderItemBindingByType(type).root.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
            }
        }
    }

    inner class SurveyMultiButtonsViewHolder(private val viewBinding: SurveyMultiButtonsBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is MultiSelection.MultiButtons) {
                with(viewBinding) {
                    textViewQuestion.text = item.question
                    imageView.setImageResource(item.resId)

                    item.buttonTexts.forEachIndexed { index, buttonText ->
                        getButtonByIndex(index)?.let {
                            it.text = buttonText.title
                            it.setOnClickListener {
                                getSelectedButtonTypeByIndex(index)?.let { selectedButtonType ->
                                    onSurveyItemClickListener.onMultiButtonsClick(item, selectedButtonType)
                                }
                            }
                        }

                        getDescriptionTextByIndex(index)?.let { textView ->
                            buttonText.description?.let {
                                textView.visible()
                                textView.text = it
                            } ?: textView.gone()
                        }
                    }

                    updateUiBySelectedButtonType(item.selectedButtonTypes)

                    if (MultiSelection.SelectedButtonType.DonTCare in item.selectedButtonTypes) {
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                    } else {
                        buttonDonTCare.backgroundTintList = ColorStateList.valueOf(donTCareUnselectedButtonColor)
                    }

                    buttonDonTCare.setOnClickListener {
                        onSurveyItemClickListener.onMultiButtonsClick(item, MultiSelection.SelectedButtonType.DonTCare)
                    }
                }
            }
        }

        private fun getButtonByIndex(index: Int) = when(index) {
            0 -> viewBinding.buttonPair1.buttonFirst
            1 -> viewBinding.buttonPair1.buttonSecond
            2 -> viewBinding.buttonPair2.buttonFirst
            3 -> viewBinding.buttonPair2.buttonSecond
            4 -> viewBinding.buttonPair3.buttonFirst
            5 -> viewBinding.buttonPair3.buttonSecond
            6 -> viewBinding.buttonPair4.buttonFirst
            7 -> viewBinding.buttonPair4.buttonSecond
            else -> null
        }

        private fun getButtonByType(selectedButtonType: MultiSelection.SelectedButtonType) = when(selectedButtonType) {
            MultiSelection.SelectedButtonType.First-> viewBinding.buttonPair1.buttonFirst
            MultiSelection.SelectedButtonType.Second -> viewBinding.buttonPair1.buttonSecond
            MultiSelection.SelectedButtonType.Third -> viewBinding.buttonPair2.buttonFirst
            MultiSelection.SelectedButtonType.Fourth -> viewBinding.buttonPair2.buttonSecond
            MultiSelection.SelectedButtonType.Fifth -> viewBinding.buttonPair3.buttonFirst
            MultiSelection.SelectedButtonType.Sixth -> viewBinding.buttonPair3.buttonSecond
            MultiSelection.SelectedButtonType.Seventh -> viewBinding.buttonPair4.buttonFirst
            MultiSelection.SelectedButtonType.Eighth -> viewBinding.buttonPair4.buttonSecond
            else -> null
        }

        private fun getDescriptionTextByIndex(index: Int) = when(index) {
            0 -> viewBinding.buttonPair1.textViewDescription1
            1 -> viewBinding.buttonPair1.textViewDescription2
            2 -> viewBinding.buttonPair2.textViewDescription1
            3 -> viewBinding.buttonPair2.textViewDescription2
            4 -> viewBinding.buttonPair3.textViewDescription1
            5 -> viewBinding.buttonPair3.textViewDescription2
            6 -> viewBinding.buttonPair4.textViewDescription1
            7 -> viewBinding.buttonPair4.textViewDescription2
            else -> null
        }

        private fun getSelectedButtonTypeByIndex(index: Int) = when(index) {
            0 -> MultiSelection.SelectedButtonType.First
            1 -> MultiSelection.SelectedButtonType.Second
            2 -> MultiSelection.SelectedButtonType.Third
            3 -> MultiSelection.SelectedButtonType.Fourth
            4 -> MultiSelection.SelectedButtonType.Fifth
            5 -> MultiSelection.SelectedButtonType.Sixth
            6 -> MultiSelection.SelectedButtonType.Seventh
            7 -> MultiSelection.SelectedButtonType.Eighth
            else -> null
        }

        private fun updateUiBySelectedButtonType(selectedButtonTypes: List<MultiSelection.SelectedButtonType>) {
            repeat(MultiSelection.MultiButtons.BUTTON_COUNT) { index ->
                getButtonByIndex(index)?.let { appCompatButton ->
                    appCompatButton.backgroundTintList = ColorStateList.valueOf(unselectedButtonColor)
                }
            }

            selectedButtonTypes.forEach { type ->
                getButtonByType(type)?.let { appCompatButton ->
                    appCompatButton.backgroundTintList = ColorStateList.valueOf(selectedButtonColor)
                }
            }
        }
    }

    inner class SurveyMustSelectViewHolder(private val viewBinding: SurveyMustSelectBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.MustSelect) {
                viewBinding.textViewQuestion.text = item.question
                viewBinding.textViewMustSelect.text = SurveyItem.MustSelect.mustSelectItemMap[item.selectedItem]?.first ?: "Q1~Q3??"
                viewBinding.linearLayoutButton.setOnClickListener {
                    onSurveyItemClickListener.onMustSelectClick(item)
                }
            }
        }
    }

    inner class SurveyFooterViewHolder(private val viewBinding: SurveyFooterBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.Footer){
                viewBinding.textViewSubmit.setOnClickListener {
                    viewBinding.textViewSubmit.setOnClickListener {
                        onSurveyItemClickListener.onSubmitClick()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            ItemViewType.Header.value -> with(SurveyHeaderBinding.inflate(inflater, parent,false)) {
                SurveyHeaderViewHolder(this)
            }
            ItemViewType.TwoButton.value -> with(SurveyTwoButtonsBinding.inflate(inflater, parent, false)) {
                SurveyTwoButtonViewHolder(this)
            }
            ItemViewType.Slider.value -> with(SurveySliderBinding.inflate(inflater, parent, false)) {
                SurveySliderViewHolder(this)
            }
            ItemViewType.MultiButtons.value -> with(SurveyMultiButtonsBinding.inflate(inflater, parent, false)) {
                SurveyMultiButtonsViewHolder(this)
            }
            ItemViewType.ThreeButton.value -> with(SurveyThreeButtonsBinding.inflate(inflater, parent, false)) {
                SurveyThreeButtonViewHolder(this)
            }
            ItemViewType.FourButton.value -> with(SurveyFourButtonsBinding.inflate(inflater, parent, false)) {
                SurveyFourButtonViewHolder(this)
            }
            ItemViewType.SixButton.value -> with(SurveySixButtonsBinding.inflate(inflater, parent, false)) {
                SurveySixButtonViewHolder(this)
            }
            ItemViewType.MustSelect.value -> with(SurveyMustSelectBinding.inflate(inflater, parent, false)) {
                SurveyMustSelectViewHolder(this)
            }
            ItemViewType.Footer.value -> with(SurveyFooterBinding.inflate(inflater, parent, false)) {
                SurveyFooterViewHolder(this)
            }
            else -> throw IllegalArgumentException("Invalid viewType :$viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is SurveyItem.Header -> ItemViewType.Header.value
            is TwoButton -> ItemViewType.TwoButton.value
            is MultiSelection.Slider -> ItemViewType.Slider.value
            is MultiSelection.MultiButtons -> ItemViewType.MultiButtons.value
            is SurveyItem.SingleSelection.ThreeButton -> ItemViewType.ThreeButton.value
            is SurveyItem.SingleSelection.FourButton -> ItemViewType.FourButton.value
            is SurveyItem.SingleSelection.SixButton -> ItemViewType.SixButton.value
            is SurveyItem.MustSelect -> ItemViewType.MustSelect.value
            is SurveyItem.Footer -> ItemViewType.Footer.value
        }
    }

    private enum class ItemViewType(val value: Int) {
        Header(0),
        TwoButton(1),
        ThreeButton(2),
        FourButton(3),
        SixButton(4),
        Slider(5),
        MultiButtons(6),
        MustSelect(7),
        Footer(8)
    }

    class DiffCallback: DiffUtil.ItemCallback<SurveyItem>() {
        override fun areItemsTheSame(oldItem: SurveyItem, newItem: SurveyItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SurveyItem, newItem: SurveyItem): Boolean {
            return oldItem == newItem
        }
    }

    private fun setBackgroundTintList(buttons: Array<Button>, @ColorInt color: Int) {
        buttons.forEach {
            it.backgroundTintList = ColorStateList.valueOf(color)
        }
    }
}

