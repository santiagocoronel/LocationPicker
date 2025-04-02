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

    private val sampleEntities = listOf(
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
    fun `When getCitiesByPrefix is called, Given cityDao returns entities, Then return mapped domain objects`() = runTest {
        `given cityDao returns matching cities`()
        val result = `when getCitiesByPrefix is called`()
        `then return domain mapped list`(result)
    }

    //region Given
    private fun `given cityDao returns matching cities`() {
        coEvery { cityDao.searchCities("B", false) } returns sampleEntities
    }
    //endregion

    //region When
    private suspend fun `when getCitiesByPrefix is called`(): List<City> {
        return repository.getCitiesByPrefix("B", false)
    }
    //endregion

    //region Then
    private fun `then return domain mapped list`(result: List<City>) {
        val expected = sampleEntities.map { it.toDomain() }
        assertEquals(expected, result)
        coVerify { cityDao.searchCities("B", false) }
    }
    //endregion
}