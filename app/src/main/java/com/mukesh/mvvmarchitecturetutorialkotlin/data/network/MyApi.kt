package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.AuthResponse
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Response<AuthResponse>


    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(networkConnectionInterceptor)
                .build()
//                .addNetworkInterceptor(networkConnectionInterceptor)

            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}