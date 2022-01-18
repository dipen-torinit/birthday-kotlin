package com.birthday.kotlin.ui.add

import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor() : BaseViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    fun onNameTextChanged(text: CharSequence) = viewModelScope.launch {
        _name.emit(text.toString())
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    fun onEmailTextChanged(text: CharSequence) = viewModelScope.launch {
        _email.emit(text.toString())
    }

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone
    fun onPhoneTextChanged(text: CharSequence) = viewModelScope.launch {
        _phone.emit(text.toString())
    }

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob
    fun onDOBTextChanged(text: CharSequence) = viewModelScope.launch {
        _dob.emit(text.toString())
    }

    val isFormValid: Flow<Boolean> = combine(
        name,
        email,
        phone,
        dob
    ) { name, email, phone, dob ->
//        mBinding.txtErrorMessage.text = ""
//        val emailIsValid = email.length > 6
//        val passwordIsValid = password.length in 7..15
//        val passwordAgainIsValid = passwordAgain == password
//        errorMessage = when {
//            emailIsValid.not() -> "email not valid"
//            passwordIsValid.not() -> "Password not valid"
//            passwordAgainIsValid.not() -> "Passwords do not match"
//            else -> null
//        }
//        errorMessage?.let {
//            mBinding.txtErrorMessage.text = it
//        }
//        emailIsValid and passwordIsValid and passwordAgainIsValid
        name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && dob.isNotEmpty()
    }


    private val _temp = MutableStateFlow("")
    val temp: StateFlow<String> = _temp
    fun setTempValue(message: String) = viewModelScope.launch {
        _temp.emit(message)
    }
}