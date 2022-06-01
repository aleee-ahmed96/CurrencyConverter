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

    private val _response = MutableLiveData<Resource<String>>()
    val response: LiveData<Resource<String>> = _response

    fun getCurrenciesList() {
        viewModelScope.launch {
            remoteRepository.getCurrencyCountriesList()
                .onStart {
                    _response.postValue(Resource.loading(null))
                }
                .catch {
                    _response.postValue(Resource.error(it.message ?: "Unknown Error", null))
                }
                .collect {
                    _response.postValue(it)
                }
        }
    }



}