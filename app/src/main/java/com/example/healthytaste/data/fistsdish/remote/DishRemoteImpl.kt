package com.example.healthytaste.data.fistsdish.remote

import com.example.healthytaste.data.remote.HealthyTasteService
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.SecondDish

class DishRemoteImpl(private val healthyTasteService: HealthyTasteService) {
    suspend fun getFirstDish(): List<First> {
        return healthyTasteService.getFirstDish()
    }
    suspend fun getOneFirstDish(firstDishId: Int): First{
        return healthyTasteService.getOneFirstDish(firstDishId)
    }
    suspend fun getSecondDish(): List<SecondDish>{
        return healthyTasteService.getSecondDish()
    }
    suspend fun getSecondDishDetail(secondDishId: Int): SecondDish{
        return healthyTasteService.getSecondDetail(secondDishId)
    }
    suspend fun getDessertDish(): List<DessertDish>{
        return healthyTasteService.getDessertDish()
    }
    suspend fun getDessertDishDetail(dessertDishId: Int): DessertDish{
        return healthyTasteService.getDessertDishDetail(dessertDishId)
    }
}