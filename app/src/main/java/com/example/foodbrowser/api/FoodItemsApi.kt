package com.example.foodbrowser.api

import com.example.foodbrowser.application.Constants.SEARCH_URL
import com.example.foodbrowser.response.FoodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodItemsApi {
    @GET(SEARCH_URL)
    suspend fun getFoodItemsFromSearch(@Query("kv") kv: String): Response<ArrayList<FoodItem>>
}