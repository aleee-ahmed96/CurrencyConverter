package com.currencyconverter.repositories

import com.currencyconverter.database.entities.CurrenciesChangeEntity
import com.currencyconverter.database.entities.CurrenciesListEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun addCurrencyList(listEntity: List<CurrenciesListEntity>)

    suspend fun getCurrenciesList(): Flow<List<CurrenciesListEntity>?>

    suspend fun addCurrencyChange(source: String, changeEntity: List<CurrenciesChangeEntity>)

    suspend fun getCurrencyChangesList(source: String): Flow<List<CurrenciesChangeEntity>?>


}