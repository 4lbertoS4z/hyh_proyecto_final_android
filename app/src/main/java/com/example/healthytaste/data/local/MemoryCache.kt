package com.example.healthytaste.data.local

import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.SecondDish

class MemoryCache {
    var firstDishList: List<First>? = null

    fun clearAllFirstDish(){
        firstDishList = null
    }
    var secondDishList: List<SecondDish>? = null

    fun clearAllSecondDish(){
        secondDishList = null
    }
    var dessertDishList: List<DessertDish>? = null

    fun clearAllDessertDish(){
        dessertDishList = null
    }
}
