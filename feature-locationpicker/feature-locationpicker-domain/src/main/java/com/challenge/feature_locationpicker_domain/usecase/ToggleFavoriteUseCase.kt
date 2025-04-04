package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.repository.LocationRepository


class ToggleFavoriteUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(cityId: Int, isFavorite: Boolean) {
        repository.toggleFavorite(cityId, !isFavorite)
    }
}