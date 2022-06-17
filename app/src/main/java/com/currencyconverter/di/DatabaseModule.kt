package com.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.currencyconverter.database.CurrencyDatabase
import com.currencyconverter.database.SharePreferences
import com.currencyconverter.database.dao.CurrencyChangeDao
import com.currencyconverter.database.dao.CurrencyListDao
import com.currencyconverter.repositories.*
import com.currencyconverter.utils.Constants.API_KEY
import com.currencyconverter.utils.Constants.API_KEY_VALUE
import com.currencyconverter.utils.Constants.BASE_URL
import com.currencyconverter.utils.Constants.DATABASE_NAME
import com.currencyconverter.utils.Constants.SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSharePreferences(@ApplicationContext context: Context): SharePreferences {
        return SharePreferences(
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCurrencyListDAO(database: CurrencyDatabase) : CurrencyListDao {
        return database.currencyListDAO()
    }

    @Provides
    fun currencyChangeDAO(database: CurrencyDatabase) : CurrencyChangeDao {
        return database.currencyChangeDAO()
    }

}
