package com.example.foodbrowser.response

import com.google.gson.annotations.SerializedName

data class FoodItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("portion")
    val portion: Int
)
