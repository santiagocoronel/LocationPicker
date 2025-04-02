package com.challenge.feature_locationpicker_data.source

import android.content.Context
import com.challenge.feature_locationpicker_remote.model.CityDto
import kotlinx.serialization.json.Json

class LocalJsonDataSource(
    private val context: Context
) {
    private val jsonParser = Json {
        ignoreUnknownKeys = true
    }

    fun readCitiesFromAssets(): List<CityDto> {
        return try {
            val inputStream = context.assets.open("cities.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            jsonParser.decodeFromString(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}