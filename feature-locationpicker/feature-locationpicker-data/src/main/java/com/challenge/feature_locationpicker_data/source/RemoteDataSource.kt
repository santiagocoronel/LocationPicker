package com.challenge.feature_locationpicker_data.source

import com.challenge.feature_locationpicker_remote.api.CityApi
import com.challenge.feature_locationpicker_remote.model.CityDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSource(
    private val api: CityApi
) {
    suspend fun fetchCities(): List<CityDto> = withContext(Dispatchers.IO) {
        try {
            api.getCities()
        } catch (e: IOException) {
            emptyList()
        } catch (e: HttpException) {
            emptyList()
        }
    }
}