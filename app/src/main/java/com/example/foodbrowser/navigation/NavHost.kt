package com.example.foodbrowser.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodbrowser.application.Constants.SEARCH_FOOD_ITEMS_SCREEN
import com.example.foodbrowser.views.SearchFoodItemsScreen
import com.example.foodbrowser.views.SearchFoodItemsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SEARCH_FOOD_ITEMS_SCREEN) {
        composable(SEARCH_FOOD_ITEMS_SCREEN) {
            val viewModel = getViewModel<SearchFoodItemsViewModel>()
            val loadSearchedFoodItemsState by viewModel.loadSearchedFoodItemsState.collectAsState()
            val searchValue by viewModel.searchValue.collectAsState()

            SearchFoodItemsScreen(
                loadFoodItemsState = loadSearchedFoodItemsState,
                searchValue = searchValue,
                setSearchValue = { searchValue -> viewModel.setSearchValue(searchValue) }
            )
        }
    }
}