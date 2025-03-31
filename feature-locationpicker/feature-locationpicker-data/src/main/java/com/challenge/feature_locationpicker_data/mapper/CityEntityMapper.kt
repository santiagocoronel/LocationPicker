package com.challenge.feature_locationpicker_data.mapper

import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.model.Coord
import com.challenge.feature_locationpicker_local.entiy.CityEntity
import com.challenge.feature_locationpicker_local.entiy.CoordEntity

fun CityEntity.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
        coord = Coord(coord.lat, coord.lon),
        isFavorite = isFavorite
    )
}

fun City.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name,
        country = country,
        coord = CoordEntity(coord.lat, coord.lon),
        isFavorite = isFavorite
    )
}