package com.birthday.kotlin.api

import com.birthday.kotlin.common.Constants
import com.birthday.kotlin.data.Person
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebasedb: FirebaseDatabase
) : APIInterface {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser != null
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser != null
    }

    override fun checkIfAlreadyLoggedIn(): Boolean {
        return firebaseAuth.currentUser?.uid?.let {
            true
        } ?: run {
            false
        }
    }

    override fun logOut(): Boolean {
        firebaseAuth.signOut()
        return true
    }

    override suspend fun fetchBirthdays(): Result<List<Person>> {
        return try {
            var list = listOf<Person>()
            firebaseAuth.currentUser?.uid?.let { uid ->
                firebasedb.getReference(uid + Constants.FIREBASE_TABLE_PEOPLE).get().await()
                    .let { dataSnapshot ->
                        list = dataSnapshot.children.map { it.getValue(Person::class.java)!! }
                    }
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addBirthday(person: Person): Result<Boolean> {
        return try {
            firebaseAuth.currentUser?.uid?.let { uid ->
                firebasedb.getReference(uid + Constants.FIREBASE_TABLE_PEOPLE).push()
                    .setValue(person.toMap()).await()
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}