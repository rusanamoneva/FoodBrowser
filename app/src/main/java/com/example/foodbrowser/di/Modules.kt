package com.example.foodbrowser.di

import com.example.foodbrowser.api.FoodItemsApi
import com.example.foodbrowser.application.Constants.BASE_URL
import com.example.foodbrowser.service.FoodItemsService
import com.example.foodbrowser.service.FoodItemsServiceImpl
import com.example.foodbrowser.useCase.SearchFoodItemUseCase
import com.example.foodbrowser.views.SearchFoodItemsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    factory { get<Retrofit>().create(FoodItemsApi::class.java)}
    factory<FoodItemsService>  { FoodItemsServiceImpl(foodItemsApi = get()) }
    factory { SearchFoodItemUseCase(foodItemsService = get()) }
    viewModel { SearchFoodItemsViewModel(searchFoodItemUseCase = get()) }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    single {
        Gson()
    }
    single {
        val okHttpClient = get<OkHttpClient>()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}