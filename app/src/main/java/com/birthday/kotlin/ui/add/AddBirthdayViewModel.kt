package com.birthday.kotlin.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.ui.BaseViewModel
import com.birthday.kotlin.utils.extention.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor() : BaseViewModel() {

    private val _name = MutableStateFlow(String.empty())
    val name: StateFlow<String> = _name
    fun onNameTextChanged(text: CharSequence) = viewModelScope.launch {
        _name.emit(text.toString())
    }

    private val _email = MutableStateFlow(String.empty())
    val email: StateFlow<String> = _email
    fun onEmailTextChanged(text: CharSequence) = viewModelScope.launch {
        _email.emit(text.toString())
    }

    private val _phone = MutableStateFlow(String.empty())
    val phone: StateFlow<String> = _phone
    fun onPhoneTextChanged(text: CharSequence) = viewModelScope.launch {
        _phone.emit(text.toString())
    }

    private val _dob = MutableStateFlow(String.empty())
    val dob: StateFlow<String> = _dob
    fun onDOBTextChanged(text: CharSequence) = viewModelScope.launch {
        _dob.emit(text.toString())
    }

    val isFormValid: LiveData<Boolean> = combine(
        name,
        email,
        phone,
        dob
    ) { name, email, phone, dob ->
        name.isNotEmpty() and email.isNotEmpty() and phone.isNotEmpty() and dob.isNotEmpty()
    }.asLiveData()
}