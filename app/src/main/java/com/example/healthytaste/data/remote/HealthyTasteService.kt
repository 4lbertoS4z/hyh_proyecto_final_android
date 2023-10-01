package com.example.healthytaste.data.remote

import com.example.healthytaste.FIRST_DISH
import com.example.healthytaste.model.FirstDishResponse
import retrofit2.http.GET

interface HealthyTasteService {
    @GET(FIRST_DISH)
    suspend fun getFirstDish():FirstDishResponse
}