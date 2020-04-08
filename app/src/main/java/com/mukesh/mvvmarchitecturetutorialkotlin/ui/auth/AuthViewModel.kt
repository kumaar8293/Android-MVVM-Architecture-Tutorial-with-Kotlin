package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.ApiException
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.Coroutines
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.NoInternetException

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null
    var name: String? = null
    var passwordConfirm: String? = null


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
                val loginResponse = userRepository.userLogin(email!!, password!!)
                loginResponse.user?.let {
                    //If user is not null
                    authListener?.onSuccess(it)
                    userRepository.insertUserToDatabase(it)
                    return@main
                }
                //If response is success but user is null
                authListener?.onFailed(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailed(e.message.toString())
            } catch (e: NoInternetException) {
                authListener?.onFailed(e.message.toString())
            }

        }
//authListener?.onSuccess(loginResponse)
    }


    fun getLoggedInUser() = userRepository.getUserDetails()

    fun signupButtonClicked(view: View) {

        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailed("Please enter your name")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailed("Please enter your email id")

            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailed("Please enter your password")
            return
        }
        if (passwordConfirm.isNullOrEmpty()) {
            authListener?.onFailed("Please confirm your password")
            return
        }
        if (password != passwordConfirm) {
            authListener?.onFailed("Password did not match")
            return
        }
        //I have create a Coroutines files for creating CoroutinesScopes

        Coroutines.main {
            try {
                val loginResponse = userRepository.userSignup(name!!, email!!, password!!)
                loginResponse.user?.let {
                    //If user is not null
                    authListener?.onSuccess(it)
                    userRepository.insertUserToDatabase(it)
                    return@main
                }
                //If response is success but user is null
                authListener?.onFailed(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailed(e.message.toString())
            } catch (e: NoInternetException) {
                authListener?.onFailed(e.message.toString())
            }

        }
//authListener?.onSuccess(loginResponse)
    }
}