package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    /* Rules for making a class Singleton
     Following are the rules that should be followed to make a class singleton:

     1.A private constructor
     2.A static reference of its class
     3.One static method
     4.Globally accessible object reference
     5.Consistency across multiple threads
 */

    companion object {
        private var instance: Retrofit? = null

        @Synchronized
        fun getInstance(): Retrofit? {

            if (instance == null) {
                synchronized(ApiClient) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()
                    instance = Retrofit.Builder()
                        .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                }

            }
            return instance
        }
    }
}