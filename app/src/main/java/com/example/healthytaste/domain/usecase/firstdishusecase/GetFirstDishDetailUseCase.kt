package com.example.healthytaste.domain.usecase.firstdishusecase

import com.example.healthytaste.domain.FirstDishRepository
import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse

class GetFirstDishDetailUseCase(
    private val firstDishRepository: FirstDishRepository
) {
    suspend fun execute(firstDishId: Int): First {
        return firstDishRepository.getOneFirstDish(firstDishId)
    }
}