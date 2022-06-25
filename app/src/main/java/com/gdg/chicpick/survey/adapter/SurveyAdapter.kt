package com.gdg.chicpick.survey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gdg.chicpick.R
import com.gdg.chicpick.databinding.SliderBinding
import com.gdg.chicpick.databinding.TwoButtonBinding
import com.gdg.chicpick.survey.model.SurveyItem
import com.gdg.chicpick.survey.model.SurveyItem.SingleSelection
import com.gdg.chicpick.survey.model.SurveyItem.SingleSelection.SelectedButtonType
import com.gdg.chicpick.survey.model.SurveyItem.SingleSelection.TwoButton

class SurveyAdapter(private val onSurveyItemClickListener: OnSurveyItemClickListener) : ListAdapter<SurveyItem, SurveyAdapter.ViewHolder>(DiffCallback()) {
    interface OnSurveyItemClickListener {
        fun onTwoButtonClick(
            twoButton: TwoButton,
            selectedButtonType: SelectedButtonType
        )
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

    inner class TwoButtonViewHolder(private val viewBinding: TwoButtonBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is TwoButton) {
                with(viewBinding) {
                    textViewQuestion.text = item.question
                    buttonFirst.text = item.firstButtonText.title
                    buttonSecond.text = item.secondButtonText.title

                    updateUiBySelectedButtonType(item.selectedButtonType)

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
                            SelectedButtonType.DON_T_CARE
                        )
                    }
                }
            }
        }

        private fun updateUiBySelectedButtonType(selectedButtonType: SelectedButtonType) {
            with(viewBinding) {
                when(selectedButtonType) {
                    SelectedButtonType.First -> {
                        buttonFirst.setBackgroundColor(selectedButtonColor)
                        buttonSecond.setBackgroundColor(selectedButtonColor)
                        buttonDonTCare.setBackgroundColor(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.Second -> {
                        buttonFirst.setBackgroundColor(unselectedButtonColor)
                        buttonSecond.setBackgroundColor(selectedButtonColor)
                        buttonDonTCare.setBackgroundColor(donTCareUnselectedButtonColor)
                    }
                    SelectedButtonType.DON_T_CARE -> {
                        buttonFirst.setBackgroundColor(unselectedButtonColor)
                        buttonSecond.setBackgroundColor(unselectedButtonColor)
                        buttonDonTCare.setBackgroundColor(selectedButtonColor)
                    }
                    else -> {
                        buttonFirst.setBackgroundColor(unselectedButtonColor)
                        buttonSecond.setBackgroundColor(unselectedButtonColor)
                        buttonDonTCare.setBackgroundColor(donTCareUnselectedButtonColor)
                    }
                }
            }
        }
    }

    inner class SliderViewHolder(private val viewBinding: SliderBinding) : ViewHolder(viewBinding) {
        override fun bind(item: SurveyItem) {
            if (item is SurveyItem.Slider) {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            ItemViewType.TwoButton.value -> with(TwoButtonBinding.inflate(inflater, parent, false)) {
                TwoButtonViewHolder(this)
            }
            ItemViewType.Slider.value -> with(SliderBinding.inflate(inflater, parent, false)) {
                SliderViewHolder(this)
            }
            else -> throw IllegalArgumentException("Invalid viewType :$viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is SurveyItem.SingleSelection.TwoButton -> ItemViewType.TwoButton.value
            is SurveyItem.Slider -> ItemViewType.Slider.value
        }
    }

    private enum class ItemViewType(val value: Int) {
        TwoButton(0),
        Slider(1)
    }

    class DiffCallback: DiffUtil.ItemCallback<SurveyItem>() {
        override fun areItemsTheSame(oldItem: SurveyItem, newItem: SurveyItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SurveyItem, newItem: SurveyItem): Boolean {
            return oldItem == newItem
        }
    }
}

