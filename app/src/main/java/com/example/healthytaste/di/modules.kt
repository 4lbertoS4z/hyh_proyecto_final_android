package com.example.healthytaste.di

import com.example.healthytaste.data.fistsdish.FirstDishDataImpl
import com.example.healthytaste.data.fistsdish.remote.FirstDishRemoteImpl
import com.example.healthytaste.data.remote.ApiClient
import com.example.healthytaste.data.remote.HealthyTasteService
import com.example.healthytaste.domain.FirstDishRepository
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.presentation.viewModel.FirstDishViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single<HealthyTasteService> { ApiClient.retrofit.create(HealthyTasteService::class.java) }
}

val dishModule = module {
    factory { FirstDishRemoteImpl(get()) }
    factory<FirstDishRepository> { FirstDishDataImpl(get()) }
    factory { GetFirstDishUseCase(get()) }

    viewModel { FirstDishViewModel(get()) }
}