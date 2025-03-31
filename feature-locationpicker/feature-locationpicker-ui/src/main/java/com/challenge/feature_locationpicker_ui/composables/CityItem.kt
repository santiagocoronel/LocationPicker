package com.challenge.feature_locationpicker_ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.challenge.feature_locationpicker_presentation.model.CityUiModel

@Composable
fun CityItem(
    city: CityUiModel,
    onClick: () -> Unit,
    onInfoClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (city.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Toggle Favorite"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Lat: ${city.coord.lat}, Lon: ${city.coord.lon}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onInfoClick) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "City Info")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Details")
            }
        }
    }
}