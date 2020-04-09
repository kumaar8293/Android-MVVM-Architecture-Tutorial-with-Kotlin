package com.mukesh.mvvmarchitecturetutorialkotlin.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val SAVE_AT = "save_at"

class PreferenceProvider(context: Context) {
    private val context = context.applicationContext

  /*  private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Context.MODE_PRIVATE",Context.MODE_PRIVATE)
*/
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(
            context
        )

    fun saveLastTimeAt(saveAt: String) {

        preferences.edit().putString(SAVE_AT, saveAt).apply()
    }

    fun getLastSaveAt(): String? {

        return preferences.getString(SAVE_AT, null)
    }
}