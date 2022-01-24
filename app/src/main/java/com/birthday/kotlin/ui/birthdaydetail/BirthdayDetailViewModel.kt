package com.birthday.kotlin.ui.birthdaydetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.birthday.kotlin.data.Person
import com.birthday.kotlin.ui.BaseViewModel
import com.birthday.kotlin.utils.extention.empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* We can directly get navArgs() value on ViewModel using SavedStateHandle, Instead of reading
* it on fragment and then passing it to ViewModel
* */
@HiltViewModel
class BirthdayDetailViewModel @Inject constructor(state: SavedStateHandle) :
    BaseViewModel() {

    private val _dummyResponse = MutableStateFlow(String.empty())
    val dummyResponse: StateFlow<String> = _dummyResponse

    init {
        //Getting the navArgs value which is passed to "BirthdayDetailFragment" from "BirthdayListFragment"
        state.get<Person>("person")?.let {
            dummyCall(it.name, it.email, it.phone, it.date)
        }
    }

    private fun dummyCall(name: String, email: String, phone: String, date: String) =
        viewModelScope.launch {
            _dummyResponse.emit("Name $name, Email $email Phone $phone DOB $date")
        }
}