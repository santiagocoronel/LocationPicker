package com.challenge.feature_locationpicker_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import com.challenge.feature_locationpicker_presentation.mapper.toUi
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_presentation.state.LocationPickerUiState
import kotlinx.coroutines.flow.*
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

    val uiState: StateFlow<LocationPickerUiState> = combine(
        _query,
        _onlyFavorites,
        _cities,
        _selectedCity
    ) { query, onlyFavorites, cities, selectedCity ->
        LocationPickerUiState(
            query = query,
            onlyFavorites = onlyFavorites,
            cities = cities,
            selectedCity = selectedCity
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocationPickerUiState())

    init {
        syncAndLoadCities()
    }

    private fun syncAndLoadCities() {
        viewModelScope.launch {
            syncCitiesUseCase() // sincroniza si es necesario
            loadCities()
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            val cities = getCitiesUseCase(_query.value, _onlyFavorites.value)
            _cities.value = cities.map { it.toUi() }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        loadCities()
    }

    fun onToggleOnlyFavorites() {
        _onlyFavorites.value = !_onlyFavorites.value
        loadCities()
    }

    fun onToggleFavorite(id: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id)
            loadCities()
        }
    }

    fun onCitySelected(city: CityUiModel) {
        _selectedCity.value = city
    }

    fun onCityInfoRequested(city: CityUiModel) {
        // implementar navegación a detalle luego
    }
}