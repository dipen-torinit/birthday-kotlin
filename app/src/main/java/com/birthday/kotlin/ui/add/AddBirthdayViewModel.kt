package com.birthday.kotlin.ui.add

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.common.Validation.isDOBValid
import com.birthday.kotlin.common.Validation.isEmailValid
import com.birthday.kotlin.common.Validation.isNameValid
import com.birthday.kotlin.common.Validation.isPhoneValid
import com.birthday.kotlin.data.Person
import com.birthday.kotlin.repository.BirthdayRepository
import com.birthday.kotlin.ui.BaseViewModel
import com.birthday.kotlin.utils.extention.empty
import com.birthday.kotlin.utils.extention.removeWhitespaces
import com.birthday.kotlin.utils.extention.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBirthdayViewModel @Inject constructor(private val birthdayRepository: BirthdayRepository) :
    BaseViewModel() {

    private val _photo = MutableStateFlow<Bitmap?>(null)
    val photo: StateFlow<Bitmap?> = _photo
    fun setPhoto(photoBitmap: Bitmap) = viewModelScope.launch {
        _photo.emit(photoBitmap)
    }

    data class AddBirthdayError(
        val nameError: String = String.empty(),
        val emailError: String = String.empty(),
        val phoneError: String = String.empty(),
        val dobError: String = String.empty(),
    )

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
    fun setPhoneNumber(phone: String) = viewModelScope.launch {
        _phone.emit(phone.removeWhitespaces())
    }

    private val _dob = MutableStateFlow(String.empty())
    val dob: StateFlow<String> = _dob
    fun onDOBTextChanged(text: CharSequence) = viewModelScope.launch {
        if (text.length == 2 || text.length == 5) {
            _dob.emit("$text/")
        } else {
            _dob.emit(text.toString())
        }
    }

    private val _error = MutableStateFlow(AddBirthdayError())
    val error: StateFlow<AddBirthdayError> = _error

    val isFormValid: LiveData<Boolean> = combine(
        name,
        email,
        phone,
        dob
    ) { name, email, phone, dob ->
        val isNameCorrect = isNameValid(name)
        val isEmailCorrect = isEmailValid(email)
        val isPhoneCorrect = isPhoneValid(phone)
        val isDOBCorrect = isDOBValid(dob)

        _error.emit(
            AddBirthdayError(
                nameError = if (isNameCorrect || name.isEmpty()) String.empty() else "Incorrect name",
                emailError = if (isEmailCorrect || email.isEmpty()) String.empty() else "Incorrect email",
                phoneError = if (isPhoneCorrect || phone.isEmpty()) String.empty() else "Incorrect phone",
                dobError = if (isDOBCorrect || dob.isEmpty()) String.empty() else "Incorrect DOB"
            )
        )
        isNameCorrect and isEmailCorrect and isPhoneCorrect and isDOBCorrect
    }.asLiveData()

    private val _addResponse = MutableSharedFlow<Result<Boolean>>()
    val addResponse = _addResponse.asSharedFlow()

    fun addBirthday() = viewModelScope.launch {
        startLoading()
        _addResponse.emit(
            birthdayRepository.addBirthday(
                Person(
                    name.value,
                    email.value,
                    phone.value,
                    dob.value,
                    photo.value?.toBase64() ?: run { String.empty() }
                )
            )
        )
        stopLoading()
    }

    fun cleanForm() = viewModelScope.launch {
        _name.emit(String.empty())
        _email.emit(String.empty())
        _phone.emit(String.empty())
        _dob.emit(String.empty())
        _photo.emit(null)
    }
}