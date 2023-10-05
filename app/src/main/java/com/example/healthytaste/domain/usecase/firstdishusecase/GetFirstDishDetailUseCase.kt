package com.example.healthytaste.domain.usecase.firstdishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.First

class GetFirstDishDetailUseCase(
    private val dishRepository: DishRepository
) {
    suspend fun execute(firstDishId: Int): First {
        return dishRepository.getOneFirstDish(firstDishId)
    }
}