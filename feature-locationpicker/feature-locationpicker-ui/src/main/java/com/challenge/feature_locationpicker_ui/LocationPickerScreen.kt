package com.challenge.feature_locationpicker_ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.challenge.feature_locationpicker_presentation.viewmodel.LocationPickerViewModel
import com.challenge.feature_locationpicker_ui.composables.CityListSection

@Composable
fun LocationPickerScreen(viewModel: LocationPickerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        CityListSection(
            cities = uiState.cities,
            query = uiState.query,
            onlyFavorites = uiState.onlyFavorites,
            onQueryChanged = viewModel::onQueryChanged,
            onToggleFavorites = viewModel::onToggleOnlyFavorites,
            onToggleCityFavorite = viewModel::onToggleFavorite,
            onCityClick = viewModel::onCitySelected,
            onCityInfoClick = viewModel::onCityInfoRequested
        )
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            CityListSection(
                modifier = Modifier.weight(1f),
                cities = uiState.cities,
                query = uiState.query,
                onlyFavorites = uiState.onlyFavorites,
                onQueryChanged = viewModel::onQueryChanged,
                onToggleFavorites = viewModel::onToggleOnlyFavorites,
                onToggleCityFavorite = viewModel::onToggleFavorite,
                onCityClick = viewModel::onCitySelected,
                onCityInfoClick = viewModel::onCityInfoRequested
            )
            MapSection(
                modifier = Modifier.weight(1f),
                city = uiState.selectedCity
            )
        }
    }
}