package com.example.foodbrowser.useCase

import com.example.foodbrowser.service.FoodItemsService

class SearchFoodItemUseCase(private val foodItemsService: FoodItemsService) {
    suspend operator fun invoke(searchValue: String) = foodItemsService.getFoodItemsFromSearch(searchValue = searchValue)
}