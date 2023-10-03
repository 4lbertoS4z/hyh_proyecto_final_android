package com.example.healthytaste.data.fistsdish.local


import com.example.healthytaste.data.local.MemoryCache
import com.example.healthytaste.model.First

class FirstDishLocalImpl(
    private val memoryCache: MemoryCache
) {
    fun getFirstDish(): List<First>{
        return memoryCache.firstDishList.orEmpty()
    }

    fun saveFirstDish(firstDish: List<First>){
        memoryCache.firstDishList = firstDish
    }

}