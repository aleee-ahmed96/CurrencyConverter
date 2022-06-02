package com.currencyconverter.repositories

import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(
    private val remoteService: RemoteService
) : RemoteRepository {

    override suspend fun getCurrenciesList(): Flow<Resource<String>> = flow {
        remoteService.getCurrencyCountriesList()
            .let {
                kotlin.runCatching {
                    if (it.isSuccessful && it.code() == 200) {
                        it.body()?.let { body -> successResponse(body) } ?: errorResponse()
                    }
                    else {
                        it.errorBody()?.let { e -> errorResponse(e.string()) } ?: errorResponse()
                    }
                }.onFailure { errorResponse() }
            }
    }

    override suspend fun convertCurrency(
        to: String,
        from: String,
        amount: String
    ): Flow<Resource<String>>  = flow {
        remoteService.convertCurrency(to, from, amount)
            .let {
                kotlin.runCatching {
                    if (it.isSuccessful && it.code() == 200) {
                        it.body()?.let { body -> successResponse(body) } ?: errorResponse()
                    }
                    else {
                        it.errorBody()?.let { e -> errorResponse(e.string()) } ?: errorResponse()
                    }
                }.onFailure { errorResponse() }
            }
    }

    override suspend fun changeCurrency(
        startDate: String,
        endDate: String,
        source: String,
        currencies: String
    ): Flow<Resource<String>>  = flow {
        remoteService.changeCurrency(startDate, endDate, source, currencies)
            .let {
                kotlin.runCatching {
                    if (it.isSuccessful && it.code() == 200) {
                        it.body()?.let { body -> successResponse(body) } ?: errorResponse()
                    }
                    else {
                        it.errorBody()?.let { e -> errorResponse(e.string()) } ?: errorResponse()
                    }
                }.onFailure { errorResponse() }
            }
    }

    private suspend fun FlowCollector<Resource<String>>.errorResponse(msg: String = "Unknown Error occurred") {
        emit(Resource.error(msg))
    }

    private suspend fun FlowCollector<Resource<String>>.successResponse(data: String) {
        emit(Resource.success(data))
    }


}