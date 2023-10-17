package com.example.healthytaste.domain.usecase.secondishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.SecondDish

class GetSecondDishDetailUseCase(
    private val dishRepository: DishRepository
) {
    suspend fun execute(secondDishId: Int): SecondDish {
        return dishRepository.getSecondDetail(secondDishId)
    }
}