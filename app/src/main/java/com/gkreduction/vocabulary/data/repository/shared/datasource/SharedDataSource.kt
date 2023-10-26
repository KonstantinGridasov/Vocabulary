package com.gkreduction.vocabulary.data.repository.shared.datasource

import android.content.Context
import android.content.SharedPreferences
import com.gkreduction.vocabulary.presentation.entity.Settings
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class SharedDataSource(val context: Context) {
    private var sharedPreferences: SharedPreferences? = null
    private var gson: Gson? = null

    companion object {
        const val SHARED_BASE =
            "com.gkreduction.vocabulary.data.repository.shared.datasource.SHARED_BASE"
        const val SETTINGS_KEY =
            "com.gkreduction.vocabulary.data.repository.shared.datasource.SETTINGS_KEY"
    }

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_BASE, Context.MODE_PRIVATE)
        gson = GsonBuilder().create()
    }

    fun saveSettings(settings: Settings): Settings {
        putString(SETTINGS_KEY, gson!!.toJson(settings, Settings::class.java))
        return settings
    }


    fun getSettings(): Settings {
        var settings: Settings? = null
        val result = getString(SETTINGS_KEY)
        if (result.isNotEmpty()) {
            settings = gson!!.fromJson(result, Settings::class.java)
        }
        if (settings == null) {
            settings = Settings(isIdiom = true, isIrVerbs = true, isWord = true)
            putString(SETTINGS_KEY, gson!!.toJson(settings, Settings::class.java))

        }
        return settings
    }

    private fun getInt(key: String): Int {
        return sharedPreferences!!.getInt(key, 0)
    }

    private fun putInt(key: String, value: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, defaultValue)
    }

    private fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return sharedPreferences!!.getString(key, "")!!
    }

    private fun putString(key: String, mStrings: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, mStrings)
        editor.apply()
    }


}