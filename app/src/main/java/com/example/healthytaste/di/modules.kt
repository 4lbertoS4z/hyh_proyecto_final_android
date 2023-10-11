package com.example.healthytaste.di

import com.example.healthytaste.data.dish.DishDataImpl
import com.example.healthytaste.data.dish.local.DishLocalImpl
import com.example.healthytaste.data.dish.remote.DishRemoteImpl
import com.example.healthytaste.data.local.MemoryCache
import com.example.healthytaste.data.remote.ApiClient
import com.example.healthytaste.data.remote.HealthyTasteService
import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.domain.usecase.dessertdishusecase.GetDessertDishDetailUseCase
import com.example.healthytaste.domain.usecase.dessertdishusecase.GetDessertDishUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishDetailUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.domain.usecase.secondishusecase.GetSecondDishDetailUseCase
import com.example.healthytaste.domain.usecase.secondishusecase.GetSecondDishUseCase
import com.example.healthytaste.presentation.viewModel.DessertDishViewModel
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import com.example.healthytaste.presentation.viewModel.SecondDishViewModel
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
    factory { GetSecondDishUseCase(get()) }
    factory { GetSecondDishDetailUseCase(get()) }
    factory { GetDessertDishUseCase(get()) }
    factory { GetDessertDishDetailUseCase(get()) }

    viewModel { FirstDishViewModel(get(),get()) }

    viewModel { SecondDishViewModel(get(),get()) }

    viewModel { DessertDishViewModel(get(),get()) }
}