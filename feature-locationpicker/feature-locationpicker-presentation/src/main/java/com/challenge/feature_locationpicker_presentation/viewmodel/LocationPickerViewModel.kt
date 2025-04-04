package com.challenge.feature_locationpicker_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import com.challenge.feature_locationpicker_presentation.mapper.toUi
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_presentation.model.Filters
import com.challenge.feature_locationpicker_presentation.state.LocationPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocationPickerViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val syncCitiesUseCase: SyncCitiesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _onlyFavorites = MutableStateFlow(false)
    private val _selectedCity = MutableStateFlow<CityUiModel?>(null)
    private val _cities = MutableStateFlow<List<CityUiModel>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    private val filtersFlow = combine(_query, _onlyFavorites) { query, onlyFavs ->
        Filters(query, onlyFavs)
    }
    val uiState: StateFlow<LocationPickerUiState> =
        combine(
            filtersFlow,
            _cities,
            _selectedCity,
            _isLoading,
            _error
        ) { filters, cities, selectedCity, isLoading, error ->
            LocationPickerUiState(
                query = filters.query,
                onlyFavorites = filters.onlyFavorites,
                cities = cities,
                selectedCity = selectedCity,
                isLoading = isLoading,
                error = error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LocationPickerUiState()
        )

    init {
        syncAndLoadCities()

        viewModelScope.launch {
            combine(_query, _onlyFavorites) { query, onlyFavs ->
                query to onlyFavs
            }
                .debounce(250)
                .collect { loadCities() }
        }
    }

    private fun syncAndLoadCities() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                syncCitiesUseCase()
                loadCities()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error during sync"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = getCitiesUseCase(_query.value, _onlyFavorites.value)
                _cities.value = result.map { it.toUi() }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load cities"
                _cities.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onToggleOnlyFavorites() {
        _onlyFavorites.value = !_onlyFavorites.value
    }

    fun onToggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id, isFavorite)
            loadCities()
        }
    }

    fun onCitySelected(city: CityUiModel) {
        _selectedCity.value = city
    }

}