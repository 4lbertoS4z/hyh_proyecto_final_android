package com.example.healthytaste.data.local

import com.example.healthytaste.model.First

class MemoryCache {
    var firstDishList: List<First>? = null

    fun clearAll(){
        firstDishList = null
    }
}
