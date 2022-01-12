package com.birthday.kotlin.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    fun signIn(email: String, password:String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("FIREBASE", "signInWithEmail:success")
//                val user = auth.currentUser
//                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("FIREBASE", "signInWithEmail:failure", it.exception)
//                Toast.makeText(baseContext, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show()
//                updateUI(null)
            }
        }
    }

}