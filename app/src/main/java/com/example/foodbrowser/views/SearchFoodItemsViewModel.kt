package com.example.foodbrowser.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodbrowser.response.FoodItem
import com.example.foodbrowser.useCase.SearchFoodItemUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFoodItemsViewModel(private val searchFoodItemUseCase: SearchFoodItemUseCase): ViewModel() {
    private val _loadSearchedFoodItemsState = MutableStateFlow<LoadFoodItems>(LoadFoodItems.None)
    val loadSearchedFoodItemsState: StateFlow<LoadFoodItems> = _loadSearchedFoodItemsState.asStateFlow()

    private val _searchValue = MutableStateFlow("")
    val searchValue: StateFlow<String> = _searchValue.asStateFlow()
    private var searchJob: Job? = null

    fun searchFoodItems(searchValue: String) {
        viewModelScope.launch {
            if (searchValue.isEmpty()) {
                _loadSearchedFoodItemsState.emit(LoadFoodItems.None)
            } else if (searchValue.length >= 3 ) {
                _loadSearchedFoodItemsState.emit(LoadFoodItems.Loading)
                searchFoodItemUseCase(searchValue = searchValue).fold(
                    onSuccess = {
                        _loadSearchedFoodItemsState.emit(LoadFoodItems.Success(it))
                    }, onFailure = {
                        _loadSearchedFoodItemsState.emit(LoadFoodItems.Error)
                    }
                )
            }
        }
    }

    fun setSearchValue(searchValue: String) {
        searchJob?.cancel()
        viewModelScope.launch {
            _searchValue.emit(searchValue)
        }
        searchJob = viewModelScope.launch {
            delay(1000)
            searchFoodItems(searchValue)
        }
    }

    sealed class LoadFoodItems {
        class Success(val foodItemsList: ArrayList<FoodItem>?): LoadFoodItems()
        object Error : LoadFoodItems()
        object None: LoadFoodItems()
        object Loading: LoadFoodItems()
    }
}