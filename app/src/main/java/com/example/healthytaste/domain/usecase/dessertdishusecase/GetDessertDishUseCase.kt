package com.example.healthytaste.domain.usecase.dessertdishusecase

import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.DessertDish

class GetDessertDishUseCase (
    private val dishRepository: DishRepository
) {

    suspend fun execute(forceRemote: Boolean = false): List<DessertDish>{
        return dishRepository.getDessertDish(forceRemote)
    }

}