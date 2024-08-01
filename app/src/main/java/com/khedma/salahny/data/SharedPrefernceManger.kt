package com.khedma.salahny.data

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREF_NAME = "user_data"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var name: String?
        get() = sharedPreferences.getString("name", "")
        set(value) = sharedPreferences.edit().putString("name", value).apply()

    var email: String?
        get() = sharedPreferences.getString("email", "")
        set(value) = sharedPreferences.edit().putString("email", value).apply()

    var phone: String?
        get() = sharedPreferences.getString("phone", "")
        set(value) = sharedPreferences.edit().putString("phone", value).apply()
}
