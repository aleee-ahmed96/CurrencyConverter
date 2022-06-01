package com.currencyconverter.repositories

import com.currencyconverter.ListModel
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(
    private val remoteService: RemoteService
) : RemoteRepository {


    override suspend fun getCurrencyCountriesList(): Flow<Resource<String>> = flow {
        println("TestLogs: started")
        remoteService.getCurrencyList()
            .let {
                println("TestLogs: isSuccessful: ${it.isSuccessful}")
                println("TestLogs: code:  ${it.code()}")
                println("TestLogs:message:  ${it.message()}")
                println("TestLogs: body: ${it.body()}")
                println("TestLogs: errorBody:  ${it.errorBody()}")

                if (it.isSuccessful && it.code() == 200) {
                    it.body()?.let { body ->
                        emit(Resource.success(body))
                    } ?: emit(Resource.error("Unknown Error occurred", null))
                } else {
                    it.errorBody()?.let { error ->
                        emit(Resource.error(it.message(), error.toString()))
                    } ?: emit(Resource.error("Unknown Error occurred", null))
                }
            }
    }

}