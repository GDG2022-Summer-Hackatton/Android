package com.gdg.chicpick.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdg.chicpick.result.model.ChickenMenuResult

class ResultViewModel : ViewModel() {
    private val _top3ChickenMenu = MutableLiveData<List<ChickenMenuResult>>()
    val top3ChickenMenu: LiveData<List<ChickenMenuResult>> get() = _top3ChickenMenu

    fun getResult() {
        _top3ChickenMenu.value = listOf(
            ChickenMenuResult("BBQ 황금 올리브", 0.98),
            ChickenMenuResult("BHC 핫 후라이드", 0.92),
            ChickenMenuResult("교촌치킨 레드 콤보", 0.86)
        )
    }
}