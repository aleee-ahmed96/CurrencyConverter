package com.currencyconverter.database.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "currencies_list")
data class CurrenciesListEntity(
        @PrimaryKey(autoGenerate = true)
        val clId: Long = System.currentTimeMillis(),
        var currency: String = ""
)
