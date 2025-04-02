package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.repository.LocationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SyncCitiesUseCaseTest {

    private lateinit var repository: LocationRepository
    private lateinit var useCase: SyncCitiesUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = SyncCitiesUseCase(repository)
    }

    @Test
    fun `When sync is needed Then repository returns true`() = runTest {
        `given sync is needed`()
        val result = `when use case is invoked`()
        `then result should be true and repository called`(result)
    }

    @Test
    fun `When sync is not needed Then repository returns false`() = runTest {
        `given sync is not needed`()
        val result = `when use case is invoked`()
        `then result should be false and repository called`(result)
    }

    //region Given
    private fun `given sync is needed`() {
        coEvery { repository.syncCitiesIfNeeded() } returns true
    }

    private fun `given sync is not needed`() {
        coEvery { repository.syncCitiesIfNeeded() } returns false
    }
    //endregion

    //region When
    private suspend fun `when use case is invoked`() = useCase()
    //endregion

    //region Then
    private fun `then result should be true and repository called`(result: Boolean) {
        assertTrue(result)
        coVerify { repository.syncCitiesIfNeeded() }
    }

    private fun `then result should be false and repository called`(result: Boolean) {
        assertFalse(result)
        coVerify { repository.syncCitiesIfNeeded() }
    }
    //endregion
}