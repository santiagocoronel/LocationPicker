package com.challenge.feature_locationpicker_local.entiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    @Embedded val coord: CoordEntity,
    val isFavorite: Boolean = false
)

data class CoordEntity(
    val lat: Double,
    val lon: Double
)