package com.currencyconverter.database

import android.content.SharedPreferences

class SharePreferences(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val LIST_CALL_TIME = "list_call_time"
        private const val CHANGE_CALL_TIME = "change_call_time"
    }

    fun saveListApiCallTime() {
        sharedPreferences.edit().putLong(
            LIST_CALL_TIME, System.currentTimeMillis()
        ).apply()
    }

    fun getListApiCallTime(): Long {
        return sharedPreferences.getLong(LIST_CALL_TIME, 0)
    }

    fun saveChangeApiCallTime() {
        sharedPreferences.edit().putLong(
            CHANGE_CALL_TIME, System.currentTimeMillis()
        ).apply()
    }

    fun getChangeApiCallTime(): Long {
        return sharedPreferences.getLong(CHANGE_CALL_TIME, 0)
    }



}