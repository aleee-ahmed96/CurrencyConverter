package com.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.currencyconverter.database.CurrencyDatabase
import com.currencyconverter.database.SharePreferences
import com.currencyconverter.repositories.*
import com.currencyconverter.utils.Constants.API_KEY
import com.currencyconverter.utils.Constants.API_KEY_VALUE
import com.currencyconverter.utils.Constants.BASE_URL
import com.currencyconverter.utils.Constants.DATABASE_NAME
import com.currencyconverter.utils.Constants.SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(60 , TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(it
                    .request()
                    .newBuilder()
                    .addHeader(API_KEY, API_KEY_VALUE)
                    .build()
                )
            }
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }



}

/*

fun injectModules() = loadModules


private val loadModules  by lazy {
    loadKoinModules(
        listOf(
            networkModules,
            databaseModules,
            repositoryModules,
            viewModelModules,
        )
    )
}


val networkModules = module {

    fun getHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(60 , TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(it
                    .request()
                    .newBuilder()
                    .addHeader(API_KEY, API_KEY_VALUE)
                    .build()
                )
            }
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(getHttpClient())
            .build()
    }

    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    single { provideRetrofit() }
    single { provideRemoteService(get()) }

}


val repositoryModules = module {

    single { LocalRepositoryImpl(get(), get()) } bind LocalRepository::class

    single { RemoteRepositoryImpl(get()) } bind RemoteRepository::class
}


val viewModelModules = module {
    viewModel { HomeViewModel(get(), get()) }
}


val databaseModules = module {

    fun provideSharePreferences(context: Context): SharePreferences {
        return SharePreferences(
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )
    }

    fun provideDatabase(context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideSharePreferences(get()) }
    single { provideDatabase(get()) }
    single { get<CurrencyDatabase>().currencyListDAO() }
    single { get<CurrencyDatabase>().currencyChangeDAO() }
}

*/
