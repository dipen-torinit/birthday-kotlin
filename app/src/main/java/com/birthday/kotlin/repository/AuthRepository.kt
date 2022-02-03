package com.birthday.kotlin.repository

import com.birthday.kotlin.api.APIInterface
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: APIInterface) {

    suspend fun signIn(email: String, password: String): Boolean {
        return apiService.signInWithEmailAndPassword(email, password)
    }

    suspend fun signUp(email: String, password: String): Boolean {
        return apiService.createUserWithEmailAndPassword(email, password)
    }

    fun checkIfAlreadyLoggedIn(): Boolean {
        return apiService.checkIfAlreadyLoggedIn()
    }

    fun logOut(): Boolean {
        return apiService.logOut()
    }
}