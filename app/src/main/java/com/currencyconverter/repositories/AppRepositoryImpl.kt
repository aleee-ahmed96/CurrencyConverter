package com.currencyconverter.repositories

import com.currencyconverter.database.SharePreferences
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val sharePreferences: SharePreferences
) : AppRepository {

    override fun setListApiTime() = sharePreferences.saveListApiCallTime()

    override fun getListApiTime(): Long = sharePreferences.getListApiCallTime()

    override fun setChangeApiTime() = sharePreferences.saveChangeApiCallTime()

    override fun getChangeApiTime(): Long = sharePreferences.getChangeApiCallTime()
}