package com.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.repositories.RemoteRepository
import com.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _currenciesList = MutableLiveData<Resource<String>>()
    val currenciesList: LiveData<Resource<String>> = _currenciesList

    fun getCurrenciesList() {
        viewModelScope.launch {
            remoteRepository.getCurrenciesList()
                .onStart {
                    _currenciesList.postValue(Resource.loading())
                }
                .catch {
                    _currenciesList.postValue(Resource.error(it.message ?: "Unknown Error"))
                }
                .collect {
                    _currenciesList.postValue(it)
                }
        }
    }




}