package com.birthday.kotlin.api

import com.birthday.kotlin.data.Person

/*
* Creating generic interface for network calls.
* We can implement it and provide implementation according to our backend configuration.
* e.g we can implement it and call Firebase server, RESTApi or Dummy data in case of testing
* */
interface APIInterface {
    //Auth API calls
    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean
    fun checkIfAlreadyLoggedIn(): Boolean
    fun logOut(): Boolean

    //Birthday calls
    suspend fun fetchBirthdays(): Result<List<Person>>
    suspend fun addBirthday(person: Person): Result<Boolean>
}