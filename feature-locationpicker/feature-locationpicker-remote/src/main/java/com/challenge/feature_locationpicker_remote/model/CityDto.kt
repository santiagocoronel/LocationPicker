package com.challenge.feature_locationpicker_remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("_id") val id: Int,
    val name: String,
    val country: String,
    val coord: CoordDto
)

@Serializable
data class CoordDto(
    val lon: Double,
    val lat: Double
)