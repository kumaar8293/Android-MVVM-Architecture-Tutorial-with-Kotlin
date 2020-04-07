package com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories

import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.MyApi
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository {

    //Suspended function ( login()) can be called from
    // Couroutine or another Suspended function
    //We need to make our function as suspended
    suspend fun userLogin(email: String, password: String): Response<AuthResponse> {

        return MyApi().login(email, password)
    }
}