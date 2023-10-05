package com.example.healthytaste.domain.usecase.secondishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.SecondDish

class GetSecondDishUseCase(
    private val dishRepository: DishRepository
) {
    suspend fun execute(forceRemote: Boolean = false): List<SecondDish>{
        return dishRepository.getSecondDish(forceRemote)
    }
}