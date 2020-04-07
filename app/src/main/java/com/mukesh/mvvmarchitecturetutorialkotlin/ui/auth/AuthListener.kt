package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailed(message: String)
}