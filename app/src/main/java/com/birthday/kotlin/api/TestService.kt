package com.birthday.kotlin.api

import com.birthday.kotlin.data.Person
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class TestService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebasedb: FirebaseDatabase
) : APIInterface {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        throw NullPointerException()
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        throw NullPointerException()
    }

    override fun checkIfAlreadyLoggedIn(): Boolean {
        return false
    }

    override fun logOut(): Boolean {
        return false
    }

    override suspend fun fetchBirthdays(): Result<List<Person>> {
        return Result.success(listOf())
    }

    override suspend fun addBirthday(person: Person): Result<Boolean> {
        return Result.success(true)
    }
}