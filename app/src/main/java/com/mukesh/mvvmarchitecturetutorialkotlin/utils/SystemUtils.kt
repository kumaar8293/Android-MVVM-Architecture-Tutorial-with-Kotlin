package com.mukesh.mvvmarchitecturetutorialkotlin.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager

object SystemUtils {
    fun hideKeyBoard(context: Activity) {
        val view = context.currentFocus
        if (view != null) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        if (info != null) {
            return info.state == NetworkInfo.State.CONNECTED
        }
        return false
    }
}