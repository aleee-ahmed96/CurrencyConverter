package com.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.database.entities.CurrenciesChangeEntity
import com.currencyconverter.database.entities.CurrenciesListEntity
import com.currencyconverter.repositories.LocalRepository
import com.currencyconverter.repositories.RemoteRepository
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) : ViewModel() {

    private val _currenciesListRemote = MutableLiveData<Resource<String>>()
    val currenciesListRemote: LiveData<Resource<String>> = _currenciesListRemote

    private val _currenciesListLocal = MutableLiveData<Resource<List<CurrenciesListEntity>>>()
    val currenciesListLocal: LiveData<Resource<List<CurrenciesListEntity>>> = _currenciesListLocal

    private val _currencyChangeLocal = MutableLiveData<Resource<String>>()
    val currencyChangeLocal: LiveData<Resource<String>> = _currencyChangeLocal

    private val _currencyChangeRemote = MutableLiveData<List<CurrenciesChangeEntity>>()
    val currencyChangeRemote: LiveData<List<CurrenciesChangeEntity>> = _currencyChangeRemote

    fun getCurrenciesList() {
        viewModelScope.launch {
            remoteRepository.getCurrenciesList()
                .onStart {
                    _currenciesListRemote.postValue(Resource.loading())
                }
                .catch {
                    _currenciesListRemote.postValue(Resource.error(it.message ?: "Unknown Error"))
                }
                .collect {
                    _currenciesListRemote.postValue(it)
                }
        }
    }

    fun getCurrencyChange(source: String) {
        viewModelScope.launch {
            remoteRepository.changeCurrency(source)
                .onStart { _currencyChangeLocal.postValue(Resource.loading()) }
                .catch {
                    _currencyChangeLocal.postValue(Resource.error(it.message ?: "Unknown Error"))
                }
                .collect {
                    _currencyChangeLocal.postValue(it)
                }
        }
    }


    fun addCurrencyListToDB(listEntity: List<CurrenciesListEntity>) {
        viewModelScope.launch {
            localRepository.addCurrencyList(listEntity)
        }
    }

    fun getCurrencyListFromDB() {
        viewModelScope.launch {
            localRepository.getCurrenciesList()
                .onStart { _currenciesListLocal.postValue(Resource.loading()) }
                .catch { _currenciesListLocal.postValue(Resource.error("Unknown Error.")) }
                .collect{
                    _currenciesListLocal.postValue(Resource.success(it))
                }
        }
    }

    fun addCurrencyChangeToDB(source: String, changeListEntity: List<CurrenciesChangeEntity>) {
        viewModelScope.launch {
            localRepository.addCurrencyChange(source, changeListEntity)
        }
    }

    fun getCurrencyChangeFromDB(source: String) {
        viewModelScope.launch {
            localRepository.getCurrencyChangesList(source)
                .catch { _currencyChangeRemote.postValue(null) }
                .collect{
                    _currencyChangeRemote.postValue(it)
                }
        }
    }




}