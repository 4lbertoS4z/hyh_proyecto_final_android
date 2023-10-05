package com.example.healthytaste.domain.usecase.dessertdishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First

class GetDessertDishDetailUseCase(
    private val dishRepository: DishRepository
) {
    suspend fun execute(dessertDishId: Int): DessertDish {
        return dishRepository.getDessertDetail(dessertDishId)
    }
}