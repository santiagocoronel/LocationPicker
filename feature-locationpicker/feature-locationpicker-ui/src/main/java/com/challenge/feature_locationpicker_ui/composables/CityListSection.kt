package com.challenge.feature_locationpicker_ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.challenge.feature_locationpicker_presentation.model.CityUiModel

@Composable
fun CityListSection(
    modifier: Modifier = Modifier,
    cities: List<CityUiModel>,
    query: String,
    onlyFavorites: Boolean,
    isLoading: Boolean,
    error: String?,
    onQueryChanged: (String) -> Unit,
    onToggleFavorites: () -> Unit,
    onToggleCityFavorite: (Int, Boolean) -> Unit,
    onCityClick: (CityUiModel) -> Unit,
    onCityInfoClick: (CityUiModel) -> Unit
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Filter") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Only favorites", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = onlyFavorites,
                onCheckedChange = { onToggleFavorites() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
            }

            cities.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No cities found.")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cities) { city ->
                        CityItem(
                            city = city,
                            onToggleFavorite = { onToggleCityFavorite(city.id, city.isFavorite) },
                            onClick = { onCityClick(city) },
                            onInfoClick = { onCityInfoClick(city) }
                        )
                    }
                }
            }
        }
    }
}