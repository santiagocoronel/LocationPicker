package com.challenge.feature_locationpicker_domain.model

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coord,
    val isFavorite: Boolean
)

data class Coord(
    val lat: Double,
    val lon: Double
)