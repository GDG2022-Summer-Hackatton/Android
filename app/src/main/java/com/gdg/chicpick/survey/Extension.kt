package com.gdg.chicpick.survey

import androidx.lifecycle.MutableLiveData

internal inline fun <T> MutableLiveData<T>.setValueAfter(block: T.() -> T) {
    value?.let {
        value = block(it)
    }
}