package com.birthday.kotlin.repository

import com.birthday.kotlin.api.APIInterface
import com.birthday.kotlin.data.Person
import javax.inject.Inject

class BirthdayRepository @Inject constructor(
    private val apiService: APIInterface
) {
    suspend fun fetchBirthdays(): Result<List<Person>> {
        return apiService.fetchBirthdays()
    }

    suspend fun addBirthday(person: Person): Result<Boolean> {
        return apiService.addBirthday(person)
    }
}