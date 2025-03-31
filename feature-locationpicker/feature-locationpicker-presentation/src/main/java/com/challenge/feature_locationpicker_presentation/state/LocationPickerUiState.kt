package com.challenge.feature_locationpicker_presentation.state

import com.challenge.feature_locationpicker_domain.model.City

data class LocationPickerUiState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
    val onlyFavorites: Boolean = false,
    val error: String? = null
)