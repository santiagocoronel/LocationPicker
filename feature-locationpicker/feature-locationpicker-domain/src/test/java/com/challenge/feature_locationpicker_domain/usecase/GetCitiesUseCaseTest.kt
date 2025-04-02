package com.challenge.feature_locationpicker_domain.usecase

import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.model.Coord
import com.challenge.feature_locationpicker_domain.repository.LocationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCitiesUseCaseTest {

    private lateinit var repository: LocationRepository
    private lateinit var useCase: GetCitiesUseCase

    private val sampleCities = listOf(
        City(1, "Alabama", "US", Coord(1.0, 1.0), false),
        City(2, "Albuquerque", "US", Coord(1.0, 1.0), true),
        City(3, "Sydney", "AU", Coord(1.0, 1.0), false),
        City(4, "Arizona", "US", Coord(1.0, 1.0), true)
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetCitiesUseCase(repository)
    }

    @Test
    fun `When invoked with prefix A, Given some cities, Then it should return those starting with A`() = runTest {
        `given cities starting with A`()
        val result = `when use case is invoked with prefix`("A", false)
        `then should return expected cities`(listOf(sampleCities[0], sampleCities[1], sampleCities[3]), result)
    }

    @Test
    fun `When invoked with prefix Al, Then return only cities starting with Al`() = runTest {
        `given cities starting with Al`()
        val result = `when use case is invoked with prefix`("Al", false)
        `then should return expected cities`(listOf(sampleCities[0], sampleCities[1]), result)
    }

    @Test
    fun `When onlyFavorites is true, Then return only favorite cities`() = runTest {
        `given only favorite cities`()
        val result = `when use case is invoked with prefix`("", true)
        `then should return expected cities`(listOf(sampleCities[1], sampleCities[3]), result)
    }

    @Test
    fun `When invoked with lowercase s, Then return cities ignoring case`() = runTest {
        `given lowercase prefix s`()
        val result = `when use case is invoked with prefix`("s", false)
        `then should return expected cities`(listOf(sampleCities[2]), result)
    }

    @Test
    fun `When prefix has no matches, Then return empty list`() = runTest {
        `given no matching cities for prefix`("Xyz")
        val result = `when use case is invoked with prefix`("Xyz", false)
        `then should return expected cities`(emptyList(), result)
    }

    //region Given
    private fun `given cities starting with A`() {
        coEvery { repository.getCitiesByPrefix("A", false) } returns listOf(
            sampleCities[0], sampleCities[1], sampleCities[3]
        )
    }

    private fun `given cities starting with Al`() {
        coEvery { repository.getCitiesByPrefix("Al", false) } returns listOf(
            sampleCities[0], sampleCities[1]
        )
    }

    private fun `given only favorite cities`() {
        coEvery { repository.getCitiesByPrefix("", true) } returns listOf(
            sampleCities[1], sampleCities[3]
        )
    }

    private fun `given lowercase prefix s`() {
        coEvery { repository.getCitiesByPrefix("s", false) } returns listOf(sampleCities[2])
    }

    private fun `given no matching cities for prefix`(prefix: String) {
        coEvery { repository.getCitiesByPrefix(prefix, false) } returns emptyList()
    }
    //endregion

    //region When
    private suspend fun `when use case is invoked with prefix`(
        prefix: String,
        onlyFavorites: Boolean
    ): List<City> {
        return useCase(prefix, onlyFavorites)
    }
    //endregion

    //region Then
    private fun `then should return expected cities`(
        expected: List<City>,
        actual: List<City>
    ) {
        assertEquals(expected, actual)
        coVerify { repository.getCitiesByPrefix(any(), any()) }
    }
    //endregion
}