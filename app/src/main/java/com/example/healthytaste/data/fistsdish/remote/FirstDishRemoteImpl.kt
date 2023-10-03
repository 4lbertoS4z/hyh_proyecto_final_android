package com.example.healthytaste.data.fistsdish.remote

import com.example.healthytaste.data.remote.HealthyTasteService
import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse

class FirstDishRemoteImpl(private val healthyTasteService: HealthyTasteService) {
    suspend fun getFirstDish(): List<First> {
        return healthyTasteService.getFirstDish()
    }
    suspend fun getOneFirstDish(firstDishId: Int): First{
        return healthyTasteService.getOneFirstDish(firstDishId)
    }
}