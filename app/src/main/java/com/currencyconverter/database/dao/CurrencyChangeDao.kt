package com.currencyconverter.database.dao

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currencyconverter.database.entities.CurrenciesChangeEntity
import kotlinx.coroutines.flow.Flow

@Keep
@Dao
interface CurrencyChangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyChanges(model: List<CurrenciesChangeEntity>)

    @Query("select * from currencies_change where currencyName like :source || '%' order by ccId asc")
    fun getAllCurrencyChanges(source: String): Flow<List<CurrenciesChangeEntity>>

    @Query("Delete from currencies_change where currencyName like :source || '%'")
    suspend fun deleteAll(source: String)


}