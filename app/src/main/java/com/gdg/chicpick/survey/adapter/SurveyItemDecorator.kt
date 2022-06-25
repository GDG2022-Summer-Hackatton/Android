package com.gdg.chicpick.survey.adapter

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.roundToInt


class SurveyItemDecorator : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val childLayoutPosition = parent.getChildLayoutPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        val unit = TypedValue.COMPLEX_UNIT_DIP
        val value = 80.0F
        val displayMetrics = view.context.resources.displayMetrics

        val bottom = TypedValue.applyDimension(unit, value, displayMetrics)

        if (childLayoutPosition < itemCount) {
            outRect.bottom = bottom.roundToInt()
        }
    }
}