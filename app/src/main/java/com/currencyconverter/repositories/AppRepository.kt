package com.currencyconverter.repositories



/*
* Used for App related functions
* */

interface AppRepository {

    fun setListApiTime()

    fun getListApiTime(): Long

    fun setChangeApiTime()

    fun getChangeApiTime(): Long

}