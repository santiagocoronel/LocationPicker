package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.repository.LocationRepository

class SyncCitiesUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.syncCitiesIfNeeded()
    }
}