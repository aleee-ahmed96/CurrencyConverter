package com.currencyconverter.repositories

import com.currencyconverter.ListModel
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteRepository {

    suspend fun getCurrencyCountriesList() : Flow<Resource<ListModel>>


}