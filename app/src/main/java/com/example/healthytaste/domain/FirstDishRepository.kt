package com.example.healthytaste.domain

import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse

interface FirstDishRepository {
    suspend fun getFirstDish(forceRemote: Boolean):List<First>
    suspend fun getOneFirstDish(firstDishId: Int): First
    fun saveFirstDish(firstDish: List<First>)
}