package com.challenge.feature_locationpicker_data.repository


import com.challenge.feature_locationpicker_data.mapper.toDomain
import com.challenge.feature_locationpicker_data.source.LocalJsonDataSource
import com.challenge.feature_locationpicker_data.source.RemoteDataSource
import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_local.db.CityDao
import com.challenge.feature_locationpicker_local.entiy.CityEntity
import com.challenge.feature_locationpicker_local.entiy.CoordEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocationRepositoryImplTest {

    private lateinit var cityDao: CityDao
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localJsonDataSource: LocalJsonDataSource
    private lateinit var repository: LocationRepositoryImpl

    private val expectedCityEntities = listOf(
        CityEntity(1, "Barcelona", "ES", CoordEntity(1.0, 2.0), isFavorite = false),
        CityEntity(2, "Berlin", "DE", CoordEntity(3.0, 4.0), isFavorite = true)
    )

    @BeforeEach
    fun setup() {
        cityDao = mockk(relaxed = true)
        remoteDataSource = mockk()
        localJsonDataSource = mockk()
        repository = LocationRepositoryImpl(cityDao, remoteDataSource, localJsonDataSource)
    }

    @Test
    fun `When getCitiesByPrefix is called, Given DAO returns cities for prefix B, Then return domain mapped cities`() = runTest {
        `Given DAO returns cities for prefix B`()
        val result = `When getCitiesByPrefix is called`()
        `Then return domain mapped cities`(result)
    }

    //region Given

    private fun `Given DAO returns cities for prefix B`() {
        coEvery { cityDao.searchCities("B", false) } returns expectedCityEntities
    }

    //endregion

    //region When

    private suspend fun `When getCitiesByPrefix is called`(): List<City> {
        return repository.getCitiesByPrefix("B", false)
    }

    //endregion

    //region Then

    private fun `Then return domain mapped cities`(result: List<City>) {
        val expected = expectedCityEntities.map { it.toDomain() }
        assertEquals(expected, result)
        coVerify { cityDao.searchCities("B", false) }
    }

    //endregion
}