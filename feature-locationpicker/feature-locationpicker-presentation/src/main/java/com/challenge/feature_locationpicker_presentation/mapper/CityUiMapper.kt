package com.challenge.feature_locationpicker_presentation.mapper

import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_presentation.model.CoordUiModel

fun City.toUi(): CityUiModel {
    return CityUiModel(
        id = id,
        name = name,
        country = country,
        coord = CoordUiModel(coord.lat, coord.lon),
        isFavorite = isFavorite
    )
}