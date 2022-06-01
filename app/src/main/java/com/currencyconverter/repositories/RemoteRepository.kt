package com.currencyconverter.repositories

import com.currencyconverter.ListModel
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getCurrencyCountriesList() : Flow<Resource<String>>


}