package com.birthday.kotlin.ui.settings

import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.repository.AuthRepository
import com.birthday.kotlin.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val authRepository: AuthRepository): BaseViewModel() {

    private val _logOut = MutableSharedFlow<Boolean>()
    val logOut = _logOut.asSharedFlow()

    fun logOut() = viewModelScope.launch {
        authRepository.logOut()
        _logOut.emit(true)
    }
}