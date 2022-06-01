package com.currencyconverter.database.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "currency")
data class CurrencyEntity(
        @PrimaryKey(autoGenerate = true)
        val cId: Long = System.currentTimeMillis(),
        var fileName: String = "Unknown",
        var fileSize: String = "0kb",
)
