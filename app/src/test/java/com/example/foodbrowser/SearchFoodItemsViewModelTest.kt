package com.example.foodbrowser

import com.example.foodbrowser.views.SearchFoodItemsViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchFoodItemsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: SearchFoodItemsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SearchFoodItemsViewModel(mockk())
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `searchFoodItems with empty search value should emit LoadFoodItems None`() = runBlocking {
        // Given
        val searchValue = ""

        // When
        viewModel.searchFoodItems(searchValue)

        // Then
        val result = viewModel.loadSearchedFoodItemsState.first()
        assert(result is SearchFoodItemsViewModel.LoadFoodItems.None)
    }

    @Test
    fun `searchFoodItems with search value length less than 3 should emit LoadFoodItems None`() = runBlocking {
        // Given
        val searchValue = "ab"

        // When
        viewModel.searchFoodItems(searchValue)

        // Then
        val result = viewModel.loadSearchedFoodItemsState.first()
        assert(result is SearchFoodItemsViewModel.LoadFoodItems.None)
    }
}
