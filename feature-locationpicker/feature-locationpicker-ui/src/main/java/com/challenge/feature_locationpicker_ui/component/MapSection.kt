package com.challenge.feature_locationpicker_ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MarkerState

@Composable
fun MapSection(
    selectedCity: CityUiModel?
) {
    val defaultLocation = LatLng(-34.6037, -58.3816) // Buenos Aires
    val cityLocation = selectedCity?.let {
        LatLng(it.coord.lat, it.coord.lon)
    } ?: defaultLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityLocation, 10f)
    }

    LaunchedEffect(key1 = cityLocation) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(cityLocation, 10f))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        selectedCity?.let {
            Marker(
                state = MarkerState(position = LatLng(it.coord.lat, it.coord.lon)),
                title = it.name
            )
        }
    }
}