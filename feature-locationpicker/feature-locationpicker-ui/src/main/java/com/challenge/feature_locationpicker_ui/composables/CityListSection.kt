package com.challenge.feature_locationpicker_ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CityListSection(
    modifier: Modifier = Modifier,
    cities: List<CityUiModel>,
    query: String,
    onlyFavorites: Boolean,
    onQueryChanged: (String) -> Unit,
    onToggleFavorites: () -> Unit,
    onToggleCityFavorite: (Int) -> Unit,
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cities) { city ->
                CityItem(
                    city = city,
                    onToggleFavorite = { onToggleCityFavorite(city.id) },
                    onClick = { onCityClick(city) },
                    onInfoClick = { onCityInfoClick(city) }
                )
            }
        }
    }
}