package com.currencyconverter.database.dao

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*
import com.currencyconverter.database.entities.CurrenciesListEntity
import kotlinx.coroutines.flow.Flow

@Keep
@Dao
interface CurrencyListDao {

    @Insert
    suspend fun addCurrency(listEntity: List<CurrenciesListEntity>)

    @Query("select * from currencies_list")
    fun getAllCurrencies(): Flow<List<CurrenciesListEntity>>

    @Query("Delete from currencies_list")
    suspend fun deleteAll()


}