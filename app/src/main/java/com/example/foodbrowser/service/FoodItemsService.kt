package com.example.foodbrowser.service

import com.example.foodbrowser.response.FoodItem

interface FoodItemsService {
    suspend fun getFoodItemsFromSearch(searchValue: String): Result<ArrayList<FoodItem>>
}