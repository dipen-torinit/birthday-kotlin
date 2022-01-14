package com.birthday.kotlin.ui.birthdaylist


import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.data.Person
import com.birthday.kotlin.repository.BirthdayRepository
import com.birthday.kotlin.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirthdayListViewModel @Inject constructor(private val birthdayRepository: BirthdayRepository): BaseViewModel(){

    private val _personsList = MutableSharedFlow<Result<List<Person>>>()
    val personsList = _personsList.asSharedFlow()

    fun fetchBirthdays() = viewModelScope.launch {
        startLoading()
        _personsList.emit(birthdayRepository.fetchBirthdays())
        stopLoading()
    }
}