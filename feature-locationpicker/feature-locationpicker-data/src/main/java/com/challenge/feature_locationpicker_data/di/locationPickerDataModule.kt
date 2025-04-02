package com.challenge.feature_locationpicker_data.di

import android.content.Context
import androidx.room.Room
import com.challenge.feature_locationpicker_data.repository.LocationRepositoryImpl
import com.challenge.feature_locationpicker_data.source.LocalJsonDataSource
import com.challenge.feature_locationpicker_data.source.RemoteDataSource
import com.challenge.feature_locationpicker_domain.repository.LocationRepository
import com.challenge.feature_locationpicker_local.db.CityDao
import com.challenge.feature_locationpicker_local.db.CityDatabase
import com.challenge.feature_locationpicker_remote.api.CityApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit


val locationPickerDataModule = module {

    // Room
    single {
        Room.databaseBuilder(get(), CityDatabase::class.java, "cities_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<CityDao> {
        get<CityDatabase>().cityDao()
    }

    // Retrofit
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/hernan-uala/")
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
    }

    single<CityApi> {
        get<Retrofit>().create(CityApi::class.java)
    }

    // DataSources
    single { RemoteDataSource(get()) }
    single { LocalJsonDataSource(get<Context>()) }

    // Repository
    single<LocationRepository> {
        LocationRepositoryImpl(
            cityDao = get(),
            remoteDataSource = get(),
            localJsonDataSource = get()
        )
    }
}