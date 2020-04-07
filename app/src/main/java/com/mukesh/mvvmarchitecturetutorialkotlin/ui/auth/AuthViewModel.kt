package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.ApiException
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

            // Every time response coming , we are checking manually is it success or failed
            //I want to do it automatically

            /* val loginResponse = UserRepository().userLogin(email!!, password!!)

             if (loginResponse.isSuccessful) {
                 authListener?.onSuccess(loginResponse.body()?.user!!)
             } else {
                 authListener?.onFailed("Error Code ${loginResponse.code()}")
             }*/

            try {
                val loginResponse = UserRepository().userLogin(email!!, password!!)
                loginResponse.user?.let {
                    //If user is not null
                    authListener?.onSuccess(loginResponse.user)
                    return@main
                }
                //If response is success but user is null
                authListener?.onFailed(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailed(e.message.toString())
            }


        }
//authListener?.onSuccess(loginResponse)
    }
}