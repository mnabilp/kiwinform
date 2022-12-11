package com.capstone.kiwinform.ui.view.onboarding

import android.content.Context
import android.content.SharedPreferences

class SaveState(context: Context, private var saveName: String?) {
    private var context: Context? = context
    private var sp: SharedPreferences? = null

    init {
        sp = context.getSharedPreferences(saveName, Context.MODE_PRIVATE)
    }

    fun setState(key: Int) {
        val editor = sp!!.edit()
        editor.putInt("key", key)
        editor.apply()
    }

    fun getState(): Int {
        return sp!!.getInt("key", 0)
    }
}