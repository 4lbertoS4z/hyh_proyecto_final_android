package com.example.healthytaste.domain

import com.example.healthytaste.model.First

interface FirstDishRepository {
    suspend fun getFirstDish():List<First>
}