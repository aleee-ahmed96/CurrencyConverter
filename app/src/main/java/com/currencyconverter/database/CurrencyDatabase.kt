package com.currencyconverter.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.currencyconverter.database.dao.CurrencyChangeDao
import com.currencyconverter.database.dao.CurrencyListDao
import com.currencyconverter.database.entities.CurrenciesChangeEntity
import com.currencyconverter.database.entities.CurrenciesListEntity


@Keep
@Database(
    version = 1,
    entities = [CurrenciesListEntity::class, CurrenciesChangeEntity::class],
    exportSchema = true
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyListDAO(): CurrencyListDao
    abstract fun currencyChangeDAO(): CurrencyChangeDao
}