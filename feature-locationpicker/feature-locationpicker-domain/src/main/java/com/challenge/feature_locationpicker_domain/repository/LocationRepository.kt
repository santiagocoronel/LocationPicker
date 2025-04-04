package com.challenge.feature_locationpicker_domain.repository

import com.challenge.feature_locationpicker_domain.model.City

interface LocationRepository {
    suspend fun getCitiesByPrefix(prefix: String, onlyFavorites: Boolean): List<City>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
    suspend fun syncCitiesIfNeeded(): Boolean
}