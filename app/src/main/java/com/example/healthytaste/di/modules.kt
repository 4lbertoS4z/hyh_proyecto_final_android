package com.example.healthytaste.di

import com.example.healthytaste.data.fistsdish.DishDataImpl
import com.example.healthytaste.data.fistsdish.local.DishLocalImpl
import com.example.healthytaste.data.fistsdish.remote.DishRemoteImpl
import com.example.healthytaste.data.local.MemoryCache
import com.example.healthytaste.data.remote.ApiClient
import com.example.healthytaste.data.remote.HealthyTasteService
import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishDetailUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single { MemoryCache() }
    single<HealthyTasteService> { ApiClient.retrofit.create(HealthyTasteService::class.java) }
}

val dishModule = module {
    factory { DishLocalImpl(get()) }
    factory { DishRemoteImpl(get()) }
    factory<DishRepository> { DishDataImpl(get(),get()) }
    factory { GetFirstDishUseCase(get()) }
    factory { GetFirstDishDetailUseCase(get()) }

    viewModel { FirstDishViewModel(get(),get()) }
}