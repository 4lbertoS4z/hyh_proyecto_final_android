package com.example.healthytaste.domain

import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.SecondDish

interface DishRepository {
    suspend fun getFirstDish(forceRemote: Boolean):List<First>
    suspend fun getOneFirstDish(firstDishId: Int): First
    suspend fun getSecondDish(forceRemote: Boolean):List<SecondDish>
    suspend fun getSecondDetail(secondDishId: Int): SecondDish
    suspend fun getDessertDish(forceRemote: Boolean): List<DessertDish>
    suspend fun getDessertDetail(dessertDishId: Int): DessertDish


    fun saveFirstDish(firstDish: List<First>)
    fun saveSecondDish(secondDish: List<SecondDish>)
    fun saveDessertDish(dessertDish: List<DessertDish>)
}