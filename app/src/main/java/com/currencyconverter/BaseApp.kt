package com.currencyconverter

import android.app.Application
import com.currencyconverter.di.injectModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@BaseApp)
            injectModules()
        }

    }
}