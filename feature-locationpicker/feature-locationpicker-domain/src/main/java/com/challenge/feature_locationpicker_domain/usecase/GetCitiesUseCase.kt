package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.repository.LocationRepository

class GetCitiesUseCase(private val repo: LocationRepository) {
    suspend operator fun invoke(prefix: String, onlyFavorites: Boolean) =
        repo.getCitiesByPrefix(prefix, onlyFavorites)
}