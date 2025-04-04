package com.challenge.feature_locationpicker_local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.feature_locationpicker_local.entiy.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM cities WHERE name LIKE :prefix || '%' AND (:onlyFavorites = 0 OR isFavorite = 1) ORDER BY name ASC")
    suspend fun searchCities(prefix: String, onlyFavorites: Boolean): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :cityId")
    suspend fun update(cityId: Int, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun countCities(): Int
}