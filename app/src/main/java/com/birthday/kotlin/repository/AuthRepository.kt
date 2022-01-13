package com.birthday.kotlin.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun signIn(email: String, password: String): FirebaseUser {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException(
            "SignInError",
            "Unable to sign in."
        )
    }

    fun checkIfAlreadyLoggedIn(): Boolean {
        return firebaseAuth.currentUser?.uid?.let {
            true
        } ?: run {
            false
        }
    }

    fun logOut(): Boolean {
        firebaseAuth.signOut()
        return true
    }
}