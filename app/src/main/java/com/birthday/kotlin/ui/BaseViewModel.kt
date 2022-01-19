package com.birthday.kotlin.ui

import androidx.lifecycle.ViewModel
import com.birthday.kotlin.data.LoadingEvent
import org.greenrobot.eventbus.EventBus

abstract class BaseViewModel : ViewModel() {

    fun startLoading() {
        EventBus.getDefault().post(LoadingEvent(isLoading = true))
    }

    fun stopLoading() {
        EventBus.getDefault().post(LoadingEvent(isLoading = false))
    }
}