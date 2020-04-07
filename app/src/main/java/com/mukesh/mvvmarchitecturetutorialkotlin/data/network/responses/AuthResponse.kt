package com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses

import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)