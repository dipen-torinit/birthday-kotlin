package com.birthday.kotlin.data

import com.birthday.kotlin.utils.extention.empty


data class Person(
    val name: String = String.empty(),
    val email: String = String.empty(),
    val phone: String = String.empty(),
    val date: String = String.empty(),
    val image: String = String.empty()
)
