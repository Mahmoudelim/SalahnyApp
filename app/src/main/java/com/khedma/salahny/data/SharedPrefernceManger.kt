package com.khedma.salahny.data

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

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

    var ImgRes: String?
        get() = sharedPreferences.getString("ImgRes", "")
        set(value) = sharedPreferences.edit().putString("ImgRes", value).apply()

    var phone: String?
        get() = sharedPreferences.getString("phone", "")
        set(value) = sharedPreferences.edit().putString("phone", value).apply()

    var State: String?
        get() = sharedPreferences.getString("state", "")
        set(value) = sharedPreferences.edit().putString("state", value).apply()
    var neghborhood: String?
        get() = sharedPreferences.getString("neghborhood", "")
        set(value) = sharedPreferences.edit().putString("neghborhood", value).apply()
    private const val KEY_FAVORITES = "favorites"
    private val gson = Gson()
    fun addFavorite(worker: Worker) {
        val favorites = getFavorites().toMutableList()
        if (favorites.none { it.name == worker.name }) {
            favorites.add(worker)
            saveFavorites(favorites)
        }
    }

    // Remove a worker from favorites
    fun removeFavorite(worker: Worker) {
        val favorites = getFavorites().toMutableList()
        favorites.removeAll { it.name == worker.name }
        saveFavorites(favorites)
    }

    // Get the list of favorite workers
    fun getFavorites(): List<Worker> {
        val json = sharedPreferences.getString(KEY_FAVORITES, null) ?: return emptyList()
        val type = object : TypeToken<List<Worker>>() {}.type
        return gson.fromJson(json, type)
    }

    // Save the favorites list to SharedPreferences
    private fun saveFavorites(favorites: List<Worker>) {
        val json = gson.toJson(favorites)
        sharedPreferences.edit().putString(KEY_FAVORITES, json).apply()
    }



}
