package com.challenge.feature_locationpicker_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_presentation.viewmodel.LocationPickerViewModel
import com.challenge.feature_locationpicker_ui.component.MapSection
import com.challenge.feature_locationpicker_ui.composables.CityListSection
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LocationPickerScreen(
    viewModel: LocationPickerViewModel = koinViewModel(),
    onNavigateToCityDetail: (CityUiModel) -> Unit,
    onNavigateToCityMap: (CityUiModel) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLandscape = maxWidth > maxHeight

        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                CityListSection(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    cities = uiState.cities,
                    query = uiState.query,
                    onlyFavorites = uiState.onlyFavorites,
                    onQueryChanged = viewModel::onQueryChanged,
                    onToggleFavorites = viewModel::onToggleOnlyFavorites,
                    onToggleCityFavorite = viewModel::onToggleFavorite,
                    onCityClick = viewModel::onCitySelected,
                    onCityInfoClick = { onNavigateToCityDetail(it) }
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    MapSection(selectedCity = uiState.selectedCity)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                CityListSection(
                    modifier = Modifier.fillMaxSize(),
                    cities = uiState.cities,
                    query = uiState.query,
                    onlyFavorites = uiState.onlyFavorites,
                    onQueryChanged = viewModel::onQueryChanged,
                    onToggleFavorites = viewModel::onToggleOnlyFavorites,
                    onToggleCityFavorite = viewModel::onToggleFavorite,
                    onCityClick = { city ->
                        viewModel.onCitySelected(city)
                        onNavigateToCityMap(city)
                    },
                    onCityInfoClick = viewModel::onCityInfoRequested
                )
            }
        }
    }
}