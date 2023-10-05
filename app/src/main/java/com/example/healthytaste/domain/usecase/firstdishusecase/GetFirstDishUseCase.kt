package com.example.healthytaste.domain.usecase.firstdishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.First

class GetFirstDishUseCase(
    private val dishRepository: DishRepository
) {

    suspend fun execute(forceRemote: Boolean = false): List<First>{
        return dishRepository.getFirstDish(forceRemote)
    }

}