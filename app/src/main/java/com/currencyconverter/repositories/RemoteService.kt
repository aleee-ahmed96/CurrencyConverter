package com.currencyconverter.repositories

import android.database.Observable
import com.currencyconverter.utils.Constants
import com.currencyconverter.utils.Constants.FIELD_AMOUNT
import com.currencyconverter.utils.Constants.FIELD_CURRENCIES
import com.currencyconverter.utils.Constants.FIELD_END_DATE
import com.currencyconverter.utils.Constants.FIELD_FROM
import com.currencyconverter.utils.Constants.FIELD_SOURCE
import com.currencyconverter.utils.Constants.FIELD_START_DATE
import com.currencyconverter.utils.Constants.FIELD_TO
import com.currencyconverter.utils.Constants.GET_CONVERT
import com.currencyconverter.utils.Constants.GET_LIST
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {


//******************************************************************************************************************************************************
    /*  CurrencyList   */
//******************************************************************************************************************************************************

    @GET(GET_LIST)
    fun getCurrencyList(): Observable<ResponseBody>



//******************************************************************************************************************************************************
    /*  CurrencyConverter   */
//******************************************************************************************************************************************************

    @GET(GET_CONVERT)
    fun convertCurrency(
        @Query(FIELD_TO) to: String?,
        @Query(FIELD_FROM) from: String?,
        @Query(FIELD_AMOUNT) amount: String?
    ): Observable<ResponseBody>


//******************************************************************************************************************************************************
    /*  CurrencyChange   */
//******************************************************************************************************************************************************

    @GET(GET_CONVERT)
    fun convertChange(
        @Query(FIELD_START_DATE) startDate: String?,
        @Query(FIELD_END_DATE) endDate: String?,
        @Query(FIELD_SOURCE) source: String?,
        @Query(FIELD_CURRENCIES) currencies: String?
    ): Observable<ResponseBody>



}