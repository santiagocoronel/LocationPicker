package com.challenge.feature_locationpicker_data.mapper

import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.model.Coord
import com.challenge.feature_locationpicker_local.entiy.CityEntity
import com.challenge.feature_locationpicker_local.entiy.CoordEntity
import com.challenge.feature_locationpicker_remote.model.CityDto

fun CityDto.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
        coord = Coord(coord.lat, coord.lon),
        isFavorite = false
    )
}

fun CityDto.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        name = name,
        country = country,
        coord = CoordEntity(coord.lat, coord.lon),
        isFavorite = false
    )
}