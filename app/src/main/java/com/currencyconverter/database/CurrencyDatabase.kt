package com.currencyconverter.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.currencyconverter.database.dao.CurrencyDao
import com.currencyconverter.database.entities.CurrencyEntity


@Keep
@Database(
    version = 1,
    entities = [CurrencyEntity::class],
    exportSchema = true
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDAO(): CurrencyDao
}