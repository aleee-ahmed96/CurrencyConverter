package com.currencyconverter.repositories

import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getCurrenciesList() : Flow<Resource<String>>

    suspend fun changeCurrency(source: String) : Flow<Resource<String>>


}