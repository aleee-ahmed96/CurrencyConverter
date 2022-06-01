package com.currencyconverter.repositories

import com.currencyconverter.ListModel
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import retrofit2.Response

class RemoteRepositoryImpl(
    private val remoteService: RemoteService
) : RemoteRepository {


    override suspend fun getCurrencyCountriesList(): Flow<Resource<ListModel>> = flow {
        println("TestLogs: started")
        remoteService.getCurrencyList()
            .let {
                println("TestLogs: ${it.isSuccessful}")
                println("TestLogs: ${it.code()}")
                println("TestLogs: ${it.message()}")
                println("TestLogs: ${it.body()}")
                println("TestLogs: ${it.errorBody()}")

                if (it.isSuccessful && it.code() == 200) {
                    it.body()?.let { body ->
                        emit(Resource.success(it.body()))
                    } ?: emit(Resource.error("Unknown Error occurred", null))
                } else {
                    it.errorBody()?.let { error ->
                        emit(Resource.error(it.message(), null))
                    } ?: emit(Resource.error("Unknown Error occurred", null))
                }
            }
    }

}