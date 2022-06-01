package com.currencyconverter.repositories

import com.currencyconverter.ListModel
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
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {


//******************************************************************************************************************************************************
    /*  CurrencyList   */
//******************************************************************************************************************************************************

    @GET(GET_LIST)
    suspend fun getCurrencyList(): Response<String>



//******************************************************************************************************************************************************
    /*  CurrencyConverter   */
//******************************************************************************************************************************************************

    @GET(GET_CONVERT)
    suspend fun convertCurrency(
        @Query(FIELD_TO) to: String?,
        @Query(FIELD_FROM) from: String?,
        @Query(FIELD_AMOUNT) amount: String?
    ): Response<ResponseBody>


//******************************************************************************************************************************************************
    /*  CurrencyChange   */
//******************************************************************************************************************************************************

    @GET(GET_CHANGE)
    suspend fun convertChange(
        @Query(FIELD_START_DATE) startDate: String?,
        @Query(FIELD_END_DATE) endDate: String?,
        @Query(FIELD_SOURCE) source: String?,
        @Query(FIELD_CURRENCIES) currencies: String?
    ): Response<ResponseBody>



}