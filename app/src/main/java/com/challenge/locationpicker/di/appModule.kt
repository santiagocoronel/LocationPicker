package com.challenge.locationpicker.di

import com.challenge.feature_locationpicker_data.di.locationPickerDataModule
import com.challenge.feature_locationpicker_domain.di.locationPickerDomainModule
import com.challenge.feature_locationpicker_presentation.di.locationPickerPresentationModule

val appModule = listOf(
    locationPickerDataModule,
    locationPickerDomainModule,
    locationPickerPresentationModule
)