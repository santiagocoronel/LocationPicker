package com.challenge.feature_locationpicker_presentation.state

import com.challenge.feature_locationpicker_presentation.model.CityUiModel

data class LocationPickerUiState(
    val cities: List<CityUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
    val onlyFavorites: Boolean = false,
    val selectedCity: CityUiModel? = null,
    val error: String? = null
)