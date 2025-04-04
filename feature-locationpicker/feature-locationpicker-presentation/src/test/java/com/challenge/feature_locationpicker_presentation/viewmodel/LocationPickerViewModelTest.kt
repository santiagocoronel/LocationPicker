package com.challenge.feature_locationpicker_presentation.viewmodel

import app.cash.turbine.test
import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.model.Coord
import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import com.challenge.feature_locationpicker_presentation.mapper.toUi
import com.challenge.feature_locationpicker_presentation.model.CityUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationPickerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var syncCitiesUseCase: SyncCitiesUseCase
    private lateinit var viewModel: LocationPickerViewModel

    private val sampleCities = listOf(
        City(1, "Alabama", "US", Coord(10.0, 20.0), false),
        City(2, "Alaska", "US", Coord(30.0, 40.0), true),
        City(3, "Arizona", "US", Coord(50.0, 60.0), false)
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCitiesUseCase = mockk()
        toggleFavoriteUseCase = mockk(relaxed = true)
        syncCitiesUseCase = mockk(relaxed = true)

        coEvery { getCitiesUseCase(any(), any()) } returns sampleCities

        viewModel = LocationPickerViewModel(
            getCitiesUseCase = getCitiesUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
            syncCitiesUseCase = syncCitiesUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When ViewModel is created, Given initial state, Then cities appear in UI state`() = runTest {
        `Then cities appear in UI state`()
    }

    @Test
    fun `When user types a query, Given query filter is mocked, Then update state and filter cities`() = runTest {
        `Given query filter returns sample cities`("Al")
        `When user types query`("Al")
        `Then query should update and cities filtered`("Al")
    }

    @Test
    fun `When toggle favorites is used, Given favorites filter is mocked, Then show only favorites`() = runTest {
        `Given favorites filter returns sample cities`()
        `When toggle favorites is called`()
        `Then UI state should show only favorites`()
    }

    @Test
    fun `When toggleFavorite is called, Given valid city id, Then update and reload cities`() = runTest {
        `Given cities are loaded`()
        `When toggleFavorite is called`(1, true)
        advanceUntilIdle()
        `Then toggleFavoriteUseCase should be invoked`(1, true)
    }

    @Test
    fun `When city is selected, Given valid city, Then selectedCity updates`() = runTest {
        val city = sampleCities[0].toUi()
        `When city is selected`(city)
        `Then selectedCity should be`(city)
    }

    @Test
    fun `When ViewModel initializes, Given sync and fetch fail, Then error is set in UI state`() = runTest {
        `Given sync and fetch fail`()
        `When ViewModel is reinitialized`()
        `Then error should be set in UI state`()
    }

    // region Given

    private fun `Given query filter returns sample cities`(query: String) {
        coEvery { getCitiesUseCase(query, false) } returns sampleCities
    }

    private fun `Given favorites filter returns sample cities`() {
        coEvery { getCitiesUseCase("", true) } returns sampleCities.filter { it.isFavorite }
    }

    private fun `Given sync and fetch fail`() {
        coEvery { syncCitiesUseCase() } throws RuntimeException("Sync failed")
        coEvery { getCitiesUseCase(any(), any()) } throws RuntimeException("Fetch failed")
    }

    private fun `Given cities are loaded`() {
        coEvery { getCitiesUseCase(any(), any()) } returns sampleCities
    }

    // endregion

    // region When

    private fun `When user types query`(query: String) {
        viewModel.onQueryChanged(query)
    }

    private fun `When toggle favorites is called`() {
        viewModel.onToggleOnlyFavorites()
    }

    private fun `When toggleFavorite is called`(id: Int, isFavorite: Boolean) {
        viewModel.onToggleFavorite(id, isFavorite)
    }

    private fun `When city is selected`(city: CityUiModel) {
        viewModel.onCitySelected(city)
    }

    private fun `When ViewModel is reinitialized`() {
        viewModel = LocationPickerViewModel(
            getCitiesUseCase = getCitiesUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
            syncCitiesUseCase = syncCitiesUseCase
        )
    }

    // endregion

    // region Then

    private fun `Then cities appear in UI state`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(sampleCities.map { it.toUi() }, state.cities)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun `Then query should update and cities filtered`(expectedQuery: String) = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(expectedQuery, state.query)
            assertEquals(sampleCities.map { it.toUi() }, state.cities)
            cancelAndConsumeRemainingEvents()
        }
        coVerify { getCitiesUseCase(expectedQuery, false) }
    }

    private fun `Then UI state should show only favorites`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertTrue(state.onlyFavorites)
            assertEquals(
                sampleCities.filter { it.isFavorite }.map { it.toUi() },
                state.cities
            )
            cancelAndConsumeRemainingEvents()
        }
        coVerify { getCitiesUseCase("", true) }
    }

    private fun `Then toggleFavoriteUseCase should be invoked`(cityId: Int, isFavorite: Boolean) {
        coVerify(exactly = 1) { toggleFavoriteUseCase(cityId, isFavorite) }
        coVerify(atLeast = 1) { getCitiesUseCase(any(), any()) }
    }

    private fun `Then selectedCity should be`(expected: CityUiModel) = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(expected, state.selectedCity)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun `Then error should be set in UI state`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertTrue(state.error != null)
            cancelAndConsumeRemainingEvents()
        }
    }

    // endregion
}