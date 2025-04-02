package com.challenge.feature_locationpicker_ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    city: CityUiModel,
    onBack: () -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "${city.name}, ${city.country}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onToggleFavorite(city.id) }) {
                        Icon(
                            imageVector = if (city.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Toggle Favorite"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Latitude: ${city.coord.lat}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Longitude: ${city.coord.lon}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Google Map Section
            val cityLatLng = LatLng(city.coord.lat, city.coord.lon)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(cityLatLng, 12f)
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                cameraPositionState = cameraPositionState
            ) {
                Marker(state = MarkerState(cityLatLng), title = city.name)
            }
        }
    }
}