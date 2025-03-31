package com.challenge.feature_locationpicker_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.feature_locationpicker_local.entiy.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}