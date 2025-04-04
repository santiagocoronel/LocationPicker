package com.challenge.feature_locationpicker_data.repository

import com.challenge.feature_locationpicker_data.mapper.toDomain
import com.challenge.feature_locationpicker_data.mapper.toEntity
import com.challenge.feature_locationpicker_data.source.LocalJsonDataSource
import com.challenge.feature_locationpicker_data.source.RemoteDataSource
import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.repository.LocationRepository
import com.challenge.feature_locationpicker_local.db.CityDao

class LocationRepositoryImpl(
    private val cityDao: CityDao,
    private val remoteDataSource: RemoteDataSource,
    private val localJsonDataSource: LocalJsonDataSource
) : LocationRepository {

    override suspend fun getCitiesByPrefix(prefix: String, onlyFavorites: Boolean): List<City> {
        return cityDao.searchCities(prefix, onlyFavorites).map { it.toDomain() }
    }

    override suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        cityDao.update(id, isFavorite)
    }

    override suspend fun syncCitiesIfNeeded(): Boolean {
        val remoteCities = remoteDataSource.fetchCities()
        val citiesToUse = if (remoteCities.isNotEmpty()) {
            remoteCities
        } else {
            localJsonDataSource.readCitiesFromAssets()
        }

        val dbCount = cityDao.countCities()
        if (dbCount != citiesToUse.size) {
            val entities = citiesToUse.map { it.toEntity() }
            cityDao.insertAll(entities)
            return true
        }

        return false
    }
}