package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.Coroutines

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

        //I have create a Coroutines files for creating CoroutinesScopes

        Coroutines.main {
            val loginResponse = UserRepository().userLogin(email!!, password!!)
            if (loginResponse.isSuccessful) {
                authListener?.onSuccess(loginResponse.body()?.user!!)
            } else {
                authListener?.onFailed("Error Code ${loginResponse.code()}")
            }
        }


        //authListener?.onSuccess(loginResponse)
    }
}