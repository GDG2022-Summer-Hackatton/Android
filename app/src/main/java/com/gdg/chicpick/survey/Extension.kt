package com.gdg.chicpick.survey

import android.view.View
import androidx.lifecycle.MutableLiveData

internal inline fun <T> MutableLiveData<T>.setValueAfter(block: T.() -> T) {
    value?.let {
        value = block(it)
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}