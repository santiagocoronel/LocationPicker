package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.repository.LocationRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var repository: LocationRepository
    private lateinit var useCase: ToggleFavoriteUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ToggleFavoriteUseCase(repository)
    }

    @Test
    fun `When invoked with city id Then repository toggles favorite`() = runTest {
        val (cityId, isFavorite) = `Given a city id and favorite true to toggle`()
        val expectedFavorite = false
        `When use case is invoked with id`(cityId, isFavorite)
        `Then repository should toggle favorite`(cityId, expectedFavorite)
    }

    //region Given
    private fun `Given a city id and favorite true to toggle`(): Pair<Int, Boolean> = Pair(43, true)
    //endregion

    //region When
    private suspend fun `When use case is invoked with id`(cityId: Int, isFavorite: Boolean) {
        useCase(cityId, isFavorite)
    }
    //endregion

    //region Then
    private fun `Then repository should toggle favorite`(cityId: Int, isFavorite: Boolean) {
        coVerify { repository.toggleFavorite(cityId, isFavorite) }
    }
    //endregion
}