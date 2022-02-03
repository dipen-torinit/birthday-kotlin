package com.birthday.kotlin.di

import com.birthday.kotlin.api.APIInterface
import com.birthday.kotlin.api.FirebaseService
import com.birthday.kotlin.api.TestService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        const val ENABLE_TEST_MODE = false
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthReference() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseDBReference() = FirebaseDatabase.getInstance()

    /*
    * Instead of providing CONCRETE implementation of Firebase we can return generic interface and use its function to make network request.
    * e.g In future if we want to move our backend implementation from firebase DB to our own server
    * we can just create new Service class and implement the new network calls
    * */
    @Provides
    @Singleton
    fun provideAPIService(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase
    ): APIInterface {
        return if (ENABLE_TEST_MODE) {
            TestService(firebaseAuth, firebaseDatabase)
        } else {
            FirebaseService(firebaseAuth, firebaseDatabase)
        }
    }
}