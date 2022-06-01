package com.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.currencyconverter.database.CurrencyDatabase
import com.currencyconverter.repositories.LocalRepository
import com.currencyconverter.repositories.RemoteRepository
import com.currencyconverter.utils.Constants.DATABASE_NAME
import com.currencyconverter.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectModules() = loadModules


private val loadModules  by lazy {
    loadKoinModules(
        listOf(
            repositoryModules,
            viewModelModules,
            databaseModules,
        )
    )
}


val repositoryModules = module {
    single { LocalRepository() }
    single { RemoteRepository() }
}


val viewModelModules = module {
    viewModel { HomeViewModel() }
}

val databaseModules = module {

    fun provideDatabase(context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDatabase(get()) }
    single { get<CurrencyDatabase>().currencyDAO() }
}

