package com.currencyconverter.database.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "currencies_change")
data class CurrenciesChangeEntity(
        @PrimaryKey(autoGenerate = true)
        val ccId: Long = System.currentTimeMillis(),
        var currencyName: String = "",
        var currencyRate: String = "",
)
