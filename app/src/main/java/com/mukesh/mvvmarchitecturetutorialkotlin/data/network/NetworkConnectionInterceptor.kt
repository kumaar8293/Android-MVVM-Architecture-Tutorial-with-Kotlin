package com.mukesh.mvvmarchitecturetutorialkotlin.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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


        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            // here getNetworkCapabilities will return NetworkCapabilities class Object
            //We don't want to store the the object so we can directly use apply {}
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork).apply {

                    result = when {

                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {
                connectivityManager?.activeNetworkInfo.also {
                    result = it != null && it.isConnected

                }
            }
        }

        /*val info = connectivityManager.activeNetworkInfo
        if (info != null) {
            return info.state == NetworkInfo.State.CONNECTED
        }*/
        return result
    }
}