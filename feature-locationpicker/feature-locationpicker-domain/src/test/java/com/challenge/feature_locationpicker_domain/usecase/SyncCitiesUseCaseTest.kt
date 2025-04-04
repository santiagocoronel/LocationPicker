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
        `Given sync is needed`()
        val result = `When use case is invoked`()
        `Then result should be true and repository called`(result)
    }

    @Test
    fun `When sync is not needed Then repository returns false`() = runTest {
        `Given sync is not needed`()
        val result = `When use case is invoked`()
        `Then result should be false and repository called`(result)
    }

    //region Given
    private fun `Given sync is needed`() {
        coEvery { repository.syncCitiesIfNeeded() } returns true
    }

    private fun `Given sync is not needed`() {
        coEvery { repository.syncCitiesIfNeeded() } returns false
    }
    //endregion

    //region When
    private suspend fun `When use case is invoked`() = useCase()
    //endregion

    //region Then
    private fun `Then result should be true and repository called`(result: Boolean) {
        assertTrue(result)
        coVerify { repository.syncCitiesIfNeeded() }
    }

    private fun `Then result should be false and repository called`(result: Boolean) {
        assertFalse(result)
        coVerify { repository.syncCitiesIfNeeded() }
    }
    //endregion
}