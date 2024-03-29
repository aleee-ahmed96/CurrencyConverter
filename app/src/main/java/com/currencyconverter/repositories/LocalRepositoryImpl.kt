package com.currencyconverter.repositories

import com.currencyconverter.database.dao.CurrencyChangeDao
import com.currencyconverter.database.dao.CurrencyListDao
import com.currencyconverter.database.entities.CurrenciesChangeEntity
import com.currencyconverter.database.entities.CurrenciesListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val currencyListDao: CurrencyListDao,
    private val currencyChangeDao: CurrencyChangeDao
) : LocalRepository {

    override suspend fun addCurrencyList(listEntity: List<CurrenciesListEntity>) {
        currencyListDao.deleteAll()
        currencyListDao.addCurrency(listEntity)
    }

    override suspend fun getCurrenciesList(): Flow<List<CurrenciesListEntity>?> {
        return flow {
            currencyListDao.getAllCurrencies().map {
                println("LocalLogs: map ")
                if (it.isNullOrEmpty().not()) emit(it)
                else emit(null)
            }.catch { emit(null) }.collect {}
        }
    }

    override suspend fun addCurrencyChange(
        source: String,
        changeEntity: List<CurrenciesChangeEntity>
    ) {
        currencyChangeDao.deleteAll(source)
        currencyChangeDao.addCurrencyChanges(changeEntity)
    }

    override suspend fun getCurrencyChangesList(source: String): Flow<List<CurrenciesChangeEntity>?> {
        return flow {
            currencyChangeDao.getAllCurrencyChanges(source).map {
                if (it.isNullOrEmpty().not()) emit(it)
                else emit(null)
            }.catch { emit(null) }.collect {}
        }
    }

}