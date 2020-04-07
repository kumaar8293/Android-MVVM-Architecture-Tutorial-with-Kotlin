package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: LiveData<String>)
    fun onFailed(message: String)
}