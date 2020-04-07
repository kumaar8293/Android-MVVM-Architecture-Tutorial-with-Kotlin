package com.mukesh.mvvmarchitecturetutorialkotlin.utils

import android.widget.EditText

object Utils {
    fun inputValidation(input: EditText): Boolean {
        val username = input.text.toString().trim()
        return username.isNotEmpty()
    }
}
