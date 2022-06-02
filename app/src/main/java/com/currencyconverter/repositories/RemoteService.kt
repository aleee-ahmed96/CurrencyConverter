package com.currencyconverter.repositories

import com.currencyconverter.utils.Constants.FIELD_AMOUNT
import com.currencyconverter.utils.Constants.FIELD_CURRENCIES
import com.currencyconverter.utils.Constants.FIELD_END_DATE
import com.currencyconverter.utils.Constants.FIELD_FROM
import com.currencyconverter.utils.Constants.FIELD_SOURCE
import com.currencyconverter.utils.Constants.FIELD_START_DATE
import com.currencyconverter.utils.Constants.FIELD_TO
import com.currencyconverter.utils.Constants.GET_CHANGE
import com.currencyconverter.utils.Constants.GET_CONVERT
import com.currencyconverter.utils.Constants.GET_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    /*
    * Used Response<String> because response we are receiving from Api cant be handleable using Model
    *
    * */

//******************************************************************************************************************************************************
    /*  CurrencyList   */
//******************************************************************************************************************************************************

    @GET(GET_LIST)
    suspend fun getCurrencyCountriesList(): Response<String>

//******************************************************************************************************************************************************
    /*  ChangeCurrency   */
//******************************************************************************************************************************************************

    @GET(GET_CHANGE)
    suspend fun changeCurrency(
        @Query(FIELD_START_DATE) startDate: String,
        @Query(FIELD_END_DATE) endDate: String,
        @Query(FIELD_SOURCE) source: String
    ): Response<String>



}