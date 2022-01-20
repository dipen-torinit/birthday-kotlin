package com.birthday.kotlin.data

import com.birthday.kotlin.utils.extention.empty
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Person(
    val name: String = String.empty(),
    val email: String = String.empty(),
    val phone: String = String.empty(),
    val date: String = String.empty(),
    val image: String = String.empty()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "date" to date,
            "image" to image,
        )
    }
}
