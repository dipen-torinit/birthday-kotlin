package com.birthday.kotlin.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    suspend fun startLoading() {
        _isLoading.emit(true)
    }

    suspend fun stopLoading() {
        _isLoading.emit(false)
    }
}