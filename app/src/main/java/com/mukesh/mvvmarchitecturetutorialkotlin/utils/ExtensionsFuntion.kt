package com.mukesh.mvvmarchitecturetutorialkotlin.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


fun EditText.isEmpty(): Boolean {

    return this.toString().trim().isEmpty()
}

fun Context.myToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.showProgressBar() {
    visibility = View.VISIBLE
}

fun ProgressBar.hideProgressBar() {
    visibility = View.GONE
}


fun Activity.hideKeyBoard() {
    val view = this.currentFocus
    if (view != null) {
        val imm =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.simpleSnackbar(message: String) {

    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.snackbarWithAction(message: String) {

    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snakebar ->
        snakebar.setAction("Ok") {
            snakebar.dismiss()
        }
    }.show()
}