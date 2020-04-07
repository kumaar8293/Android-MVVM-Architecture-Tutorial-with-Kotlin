package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {

        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailed("Invalid email or password")

            return
        }

        val loginResponse= UserRepository().userLogin(email!!,password!!)
        authListener?.onSuccess(loginResponse)
    }
}