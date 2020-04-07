package com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories

import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.MyApi
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.SafeApiRequest
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.AuthResponse

class UserRepository : SafeApiRequest() {

    //Suspended function ( login()) can be called from
    // Couroutine or another Suspended function
    //We need to make our function as suspended
    suspend fun userLogin(email: String, password: String): AuthResponse {

        return apiRequest {
            MyApi().login(email, password)
        }
        //  return MyApi().login(email, password)

    }


}