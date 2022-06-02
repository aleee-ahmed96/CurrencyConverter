package com.currencyconverter.repositories

import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getCurrenciesList() : Flow<Resource<String>>

    suspend fun convertCurrency(
        to: String,
        from: String,
        amount: String
    ) : Flow<Resource<String>>

    suspend fun changeCurrency(
        startDate: String,
        endDate: String,
        source: String,
        currencies: String
    ) : Flow<Resource<String>>


}