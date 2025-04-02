package com.challenge.feature_locationpicker_presentation.viewmodel

import app.cash.turbine.test
import com.challenge.feature_locationpicker_domain.model.City
import com.challenge.feature_locationpicker_domain.model.Coord
import com.challenge.feature_locationpicker_domain.usecase.GetCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.SyncCitiesUseCase
import com.challenge.feature_locationpicker_domain.usecase.ToggleFavoriteUseCase
import com.challenge.feature_locationpicker_presentation.mapper.toUi
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
    fun setUp() {
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
    fun `Given initial state When ViewModel is created Then cities appear in UI state`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(sampleCities.map { it.toUi() }, state.cities)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `When query changes Then update state and fetch cities`() = runTest {
        `given filtered result for query "Al"`()
        viewModel.onQueryChanged("Al")
        `then query should update and cities filtered`("Al")
        coVerify { getCitiesUseCase("Al", false) }
    }

    @Test
    fun `When toggleOnlyFavorites is called Then filter only favorites`() = runTest {
        val filtered = sampleCities.filter { it.isFavorite }
        coEvery { getCitiesUseCase("", true) } returns filtered

        viewModel.onToggleOnlyFavorites()

        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertTrue(state.onlyFavorites)
            assertEquals(filtered.map { it.toUi() }, state.cities)
            cancelAndConsumeRemainingEvents()
        }

        coVerify { getCitiesUseCase("", true) }
    }

    @Test
    fun `When toggleFavorite is called Then call toggleFavoriteUseCase and reload cities`() = runTest {
        viewModel.onToggleFavorite(1)
        advanceUntilIdle()
        coVerify(exactly = 1) { toggleFavoriteUseCase(1) }
        coVerify(atLeast = 1) { getCitiesUseCase(any(), any()) }
    }

    @Test
    fun `When city is selected Then selectedCity updates`() = runTest {
        val city = sampleCities[0].toUi()
        viewModel.onCitySelected(city)

        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(city, state.selectedCity)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `When sync or getCities fails Then error is set in UI state`() = runTest {
        coEvery { syncCitiesUseCase() } throws RuntimeException("Sync failed")
        coEvery { getCitiesUseCase(any(), any()) } throws RuntimeException("Fetch failed")

        viewModel = LocationPickerViewModel(
            getCitiesUseCase = getCitiesUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
            syncCitiesUseCase = syncCitiesUseCase
        )

        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertTrue(state.error != null)
            cancelAndConsumeRemainingEvents()
        }
    }

    //region Given

    private fun `given filtered result for query "Al"`() {
        coEvery { getCitiesUseCase("Al", false) } returns sampleCities
    }

    //endregion

    //region Then

    private fun `then query should update and cities filtered`(query: String) = runTest {
        viewModel.uiState.test {
            skipItems(1)
            val state = awaitItem()
            assertEquals(query, state.query)
            assertEquals(sampleCities.map { it.toUi() }, state.cities)
            cancelAndConsumeRemainingEvents()
        }
    }

    //endregion
}