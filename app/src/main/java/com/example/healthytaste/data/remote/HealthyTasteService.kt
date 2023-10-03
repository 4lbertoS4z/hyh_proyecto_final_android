package com.example.healthytaste.data.remote

import com.example.healthytaste.FIRST_DISH
import com.example.healthytaste.FIRST_DISH_DETAIL
import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HealthyTasteService {
    @GET(FIRST_DISH)
    suspend fun getFirstDish():FirstDishResponse

    @GET(FIRST_DISH_DETAIL)
    suspend fun getOneFirstDish(@Path("firstDishId")firstDishId: Int): First
  //  @Path("nombre")
}