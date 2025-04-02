package com.challenge.feature_locationpicker_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
    ) {
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
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    onQueryChanged = viewModel::onQueryChanged,
                    onToggleFavorites = viewModel::onToggleOnlyFavorites,
                    onToggleCityFavorite = viewModel::onToggleFavorite,
                    onCityClick = { city ->
                        viewModel.onCitySelected(city)
                        onNavigateToCityMap(city)
                    },
                    onCityInfoClick = onNavigateToCityDetail
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
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    onQueryChanged = viewModel::onQueryChanged,
                    onToggleFavorites = viewModel::onToggleOnlyFavorites,
                    onToggleCityFavorite = viewModel::onToggleFavorite,
                    onCityClick = { city ->
                        viewModel.onCitySelected(city)
                        onNavigateToCityMap(city)
                    },
                    onCityInfoClick = onNavigateToCityDetail
                )
            }
        }
    }
}