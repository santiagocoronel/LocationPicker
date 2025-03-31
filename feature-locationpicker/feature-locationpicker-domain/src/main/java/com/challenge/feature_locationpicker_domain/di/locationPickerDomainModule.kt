package com.challenge.feature_locationpicker_domain.di

import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val locationPickerDomainModule = module {
    factory { GetCitiesUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { SyncCitiesUseCase(get()) }
}