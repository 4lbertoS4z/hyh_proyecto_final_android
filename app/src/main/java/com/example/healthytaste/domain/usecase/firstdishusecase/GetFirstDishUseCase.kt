package com.example.healthytaste.domain.usecase.firstdishusecase

import com.example.healthytaste.domain.FirstDishRepository
import com.example.healthytaste.model.First

class GetFirstDishUseCase(
    private val firstDishRepository: FirstDishRepository
) {

    suspend fun execute(forceRemote: Boolean = false): List<First>{
        return firstDishRepository.getFirstDish(forceRemote)
    }

}