package com.challenge.feature_locationpicker_ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.challenge.feature_locationpicker_ui.composables.CityListSection
import org.junit.Rule
import org.junit.Test

class CityListSectionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var currentQuery = ""

    private companion object {
        const val QUERY = "New York"
        const val EXPECTED_RESULT = "New York"
    }

    @Test
    fun given_empty_query_when_user_types_in_filter_field_then_query_is_updated() {
        given_empty_query()
        when_user_types_in_filter_field(QUERY)
        then_query_is_updated(EXPECTED_RESULT)
    }

    private fun given_empty_query() {
        composeTestRule.setContent {
            CityListSection(
                modifier = Modifier.fillMaxSize(),
                cities = emptyList(),
                query = currentQuery,
                onlyFavorites = false,
                isLoading = false,
                error = null,
                onQueryChanged = { currentQuery = it },
                onToggleFavorites = {},
                onToggleCityFavorite = {},
                onCityClick = {},
                onCityInfoClick = {}
            )
        }
    }

    private fun when_user_types_in_filter_field(text: String) {
        composeTestRule
            .onNodeWithText("Filter")
            .performTextInput(text)
    }

    private fun then_query_is_updated(expected: String) {
        composeTestRule.runOnIdle {
            assert(currentQuery == expected)
        }
    }

    @Test
    fun given_switch_is_off_when_user_toggles_favorites_switch_then_state_is_updated() {
        given_switch_is_off()
        when_user_toggles_favorites_switch()
        then_state_is_updated_to_true()
    }

    private var onlyFavorites = false

    private fun given_switch_is_off() {
        onlyFavorites = false
        composeTestRule.setContent {
            CityListSection(
                modifier = Modifier.fillMaxSize(),
                cities = emptyList(),
                query = "",
                onlyFavorites = onlyFavorites,
                isLoading = false,
                error = null,
                onQueryChanged = {},
                onToggleFavorites = { onlyFavorites = !onlyFavorites },
                onToggleCityFavorite = {},
                onCityClick = {},
                onCityInfoClick = {}
            )
        }
    }

    private fun when_user_toggles_favorites_switch() {
        composeTestRule
            .onAllNodes(isToggleable())
            .onFirst()
            .performClick()
    }

    private fun then_state_is_updated_to_true() {
        composeTestRule.runOnIdle {
            assert(onlyFavorites)
        }
    }
}