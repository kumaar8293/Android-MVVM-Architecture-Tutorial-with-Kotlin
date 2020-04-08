package com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories

import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.AppDatabase
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.MyApi
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.SafeApiRequest
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.AuthResponse

class UserRepository(
    private val db: AppDatabase,
    private val myApi: MyApi
) : SafeApiRequest() {

    //Suspended function ( login()) can be called from
    // Couroutine or another Suspended function
    //We need to make our function as suspended
    suspend fun userLogin(email: String, password: String): AuthResponse {

        return apiRequest {
            myApi.login(email, password)
        }
        //  return MyApi().login(email, password)

    }

    suspend fun insertUserToDatabase(user: User) = db.getUserDoa().insertUser(user)

    fun getUserDetails() = db.getUserDoa().getUserData()

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ): AuthResponse {

        return apiRequest {
            myApi.userSignup(email, password, name)
        }

    }
}