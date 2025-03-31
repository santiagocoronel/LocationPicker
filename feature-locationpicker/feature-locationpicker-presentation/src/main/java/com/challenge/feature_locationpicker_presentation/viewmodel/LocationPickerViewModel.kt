package com.challenge.feature_locationpicker_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import com.challenge.feature_locationpicker_presentation.state.LocationPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationPickerViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val syncCitiesUseCase: SyncCitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationPickerUiState())
    val uiState: StateFlow<LocationPickerUiState> = _uiState

    init {
        syncAndLoadCities()
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
        searchCities()
    }

    fun onToggleFavoritesOnly(onlyFavorites: Boolean) {
        _uiState.value = _uiState.value.copy(onlyFavorites = onlyFavorites)
        searchCities()
    }

    fun onToggleFavorite(cityId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(cityId)
            searchCities()
        }
    }

    private fun syncAndLoadCities() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            syncCitiesUseCase()

            searchCities()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private fun searchCities() {
        viewModelScope.launch {
            val filtered = getCitiesUseCase(
                prefix = _uiState.value.query,
                onlyFavorites = _uiState.value.onlyFavorites
            )
            _uiState.value = _uiState.value.copy(cities = filtered)
        }
    }
}