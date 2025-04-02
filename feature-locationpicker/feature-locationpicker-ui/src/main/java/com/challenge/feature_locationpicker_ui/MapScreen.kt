package com.challenge.feature_locationpicker_ui

import androidx.compose.runtime.Composable
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_ui.component.MapSection
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    city: CityUiModel?,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "${city?.name}, ${city?.country}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            MapSection(selectedCity = city)
        }
    }
}