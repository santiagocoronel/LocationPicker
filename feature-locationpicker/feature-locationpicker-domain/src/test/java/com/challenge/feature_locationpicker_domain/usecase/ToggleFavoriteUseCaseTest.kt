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
        val cityId = `given a city id to toggle`()
        `when use case is invoked with id`(cityId)
        `then repository should toggle favorite`(cityId)
    }

    //region Given
    private fun `given a city id to toggle`(): Int = 42
    //endregion

    //region When
    private suspend fun `when use case is invoked with id`(cityId: Int) {
        useCase(cityId)
    }
    //endregion

    //region Then
    private fun `then repository should toggle favorite`(cityId: Int) {
        coVerify { repository.toggleFavorite(cityId) }
    }
    //endregion
}