package com.challenge.locationpicker.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import com.challenge.feature_locationpicker_ui.CityDetailScreen
import com.challenge.feature_locationpicker_ui.LocationPickerScreen
import com.challenge.feature_locationpicker_ui.MapScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOCATION_PICKER
    ) {
        composable(NavRoutes.LOCATION_PICKER) {
            LocationPickerScreen(
                onNavigateToCityDetail = { city ->
                    val cityJson = Uri.encode(Json.encodeToString(city))
                    navController.navigate("${NavRoutes.CITY_DETAIL}/$cityJson")
                },
                onNavigateToCityMap = { city ->
                    val cityJson = Uri.encode(Json.encodeToString(city))
                    navController.navigate("${NavRoutes.CITY_MAP}/$cityJson")
                }
            )
        }

        composable(
            route = "${NavRoutes.CITY_DETAIL}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("city")
            val city = Json.decodeFromString<CityUiModel>(cityJson!!)

            CityDetailScreen(
                city = city,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${NavRoutes.CITY_MAP}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("city")
            val city = Json.decodeFromString<CityUiModel>(cityJson!!)

            MapScreen(
                city = city,
                onBack = { navController.popBackStack() }
            )
        }
    }
}