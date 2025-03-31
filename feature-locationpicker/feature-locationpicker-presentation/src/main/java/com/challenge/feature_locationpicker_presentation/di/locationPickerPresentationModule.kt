package com.challenge.feature_locationpicker_presentation.di

import com.challenge.feature_locationpicker_presentation.viewmodel.LocationPickerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationPickerPresentationModule = module {
    viewModel {
        LocationPickerViewModel(
            getCitiesUseCase = get(),
            toggleFavoriteUseCase = get(),
            syncCitiesUseCase = get()
        )
    }
}