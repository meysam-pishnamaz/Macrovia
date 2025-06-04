package com.dante.macrovia.repository

import android.content.Context
import android.content.SharedPreferences
import com.dante.macrovia.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import androidx.core.content.edit

class SharedPrefsRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") // For Date serialization
        .create()

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit {
            putString(KEY_USER, userJson)
                .putBoolean(KEY_USER_EXISTS, true)
        }
    }

    fun getUser(): User? {
        val userJson = prefs.getString(KEY_USER, null)
        return userJson?.let { gson.fromJson(it, User::class.java) }
    }

    fun userExists(): Boolean {
        return prefs.getBoolean(KEY_USER_EXISTS, false)
    }

    fun clearUser() {
        prefs.edit {
            remove(KEY_USER)
                .putBoolean(KEY_USER_EXISTS, false)
        }
    }

    companion object {
        private const val PREFS_NAME = "macrovia_prefs"
        private const val KEY_USER = "user_data"
        private const val KEY_USER_EXISTS = "user_exists"
    }
}