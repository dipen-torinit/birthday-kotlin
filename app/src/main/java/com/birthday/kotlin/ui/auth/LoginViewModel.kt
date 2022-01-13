package com.birthday.kotlin.ui.auth

import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.repository.AuthRepository
import com.birthday.kotlin.ui.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    private val _isLogIn = MutableSharedFlow<Result<FirebaseUser>>()
    val isLogIn = _isLogIn.asSharedFlow()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        startLoading()
        try {
            authRepository.signIn(email, password).let {
                _isLogIn.emit(Result.success(it))
                stopLoading()
            }
        } catch (e: Exception) {
            _isLogIn.emit(Result.failure(e))
            stopLoading()
        }
    }

    private val _isAlreadyLoggedIn = MutableSharedFlow<Boolean>()
    val isAlreadyLoggedIn = _isAlreadyLoggedIn.asSharedFlow()

    fun checkIfAlreadyLoggedIn() = viewModelScope.launch {
        _isAlreadyLoggedIn.emit(authRepository.checkIfAlreadyLoggedIn())
    }
}