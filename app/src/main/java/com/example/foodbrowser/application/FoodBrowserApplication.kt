package com.example.foodbrowser.application

import android.app.Application
import com.example.foodbrowser.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class FoodBrowserApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@FoodBrowserApplication)
            modules(appModule)
        }
    }
}