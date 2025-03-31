package com.challenge.feature_locationpicker_presentation.model

data class CityUiModel(
    val id: Int,
    val name: String,
    val country: String,
    val coord: CoordUiModel,
    val isFavorite: Boolean
)

data class CoordUiModel(
    val lat: Double,
    val lon: Double
)