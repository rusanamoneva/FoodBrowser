package com.example.foodbrowser.service

import com.example.foodbrowser.api.FoodItemsApi
import com.example.foodbrowser.application.Constants.NO_ITEMS_CODE
import com.example.foodbrowser.exception.NoItemsException
import com.example.foodbrowser.response.FoodItem
import kotlin.Exception

class FoodItemsServiceImpl(private val foodItemsApi: FoodItemsApi): FoodItemsService {
    override suspend fun getFoodItemsFromSearch(searchValue: String): Result<ArrayList<FoodItem>> {
        return try {
            val response = foodItemsApi.getFoodItemsFromSearch(kv = searchValue)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(NoItemsException(code = NO_ITEMS_CODE))
            } else {
                Result.failure(NoItemsException(code = NO_ITEMS_CODE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}