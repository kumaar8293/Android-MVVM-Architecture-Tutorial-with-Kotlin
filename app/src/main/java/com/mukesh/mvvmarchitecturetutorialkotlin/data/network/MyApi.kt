package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface MyApi {

    //We are using suspended function here for coroutine
    //Suspending function are the center of the everything in Coroutine
    //Suspending function can be paused and resume at later time
    //It is used for long running process without blocking main thread
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(): MyApi {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MyApi::class.java)
        }
    }
}