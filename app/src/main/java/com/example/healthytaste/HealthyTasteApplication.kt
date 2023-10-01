package com.example.healthytaste

import android.app.Application
import com.example.healthytaste.di.baseModule
import com.example.healthytaste.di.dishModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HealthyTasteApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@HealthyTasteApplication)
            modules(listOf(baseModule, dishModule)).allowOverride(true)
        }
    }
}