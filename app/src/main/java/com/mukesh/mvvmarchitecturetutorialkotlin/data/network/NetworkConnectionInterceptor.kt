package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isConnected()) {
            throw NoInternetException("Please check your Internet connection!!")
        }
        return chain.proceed(chain.request())

    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
        /*val info = connectivityManager.activeNetworkInfo
        if (info != null) {
            return info.state == NetworkInfo.State.CONNECTED
        }*/
    }
}