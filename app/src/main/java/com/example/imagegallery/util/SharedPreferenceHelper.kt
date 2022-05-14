package com.example.imagegallery.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.commit()
    }

    var SharedPreferences.recentSearch
        get() = getString(Constants.RECENT_SEARCH, null)
        set(value) {
            editMe {
                it.putString(Constants.RECENT_SEARCH, value)
            }
        }
}