package com.example.healthytaste.data.fistsdish

import com.example.healthytaste.data.fistsdish.remote.FirstDishRemoteImpl
import com.example.healthytaste.domain.FirstDishRepository
import com.example.healthytaste.model.First

class FirstDishDataImpl(private val firstDishRemoteImpl:FirstDishRemoteImpl): FirstDishRepository {
    override suspend fun getFirstDish(): List<First> {
        return firstDishRemoteImpl.getFirstDish()
    }
}