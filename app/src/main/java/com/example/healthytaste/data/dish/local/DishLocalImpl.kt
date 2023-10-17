package com.example.healthytaste.data.dish.local


import com.example.healthytaste.data.local.MemoryCache
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.SecondDish

class DishLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getFirstDish(): List<First> {
        return memoryCache.firstDishList.orEmpty()
    }

    fun getSecondDish(): List<SecondDish> {
        return memoryCache.secondDishList.orEmpty()
    }

    fun getDessertDish(): List<DessertDish> {
        return memoryCache.dessertDishList.orEmpty()
    }

    fun saveFirstDish(firstDish: List<First>) {
        memoryCache.firstDishList = firstDish
    }

    fun saveSecondDish(secondDish: List<SecondDish>) {
        memoryCache.secondDishList = secondDish
    }

    fun saveDessertDish(dessertDish: List<DessertDish>) {
        memoryCache.dessertDishList = dessertDish
    }
}