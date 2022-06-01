package com.currencyconverter.database.dao

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currencyconverter.database.entities.CurrencyEntity

@Keep
@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(bookmarksEntity: CurrencyEntity)

    @Query("select * from currency order by cId desc")
    fun getAllCurrencies(): LiveData<List<CurrencyEntity>>

}