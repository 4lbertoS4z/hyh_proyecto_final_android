package com.example.healthytaste.data.remote

import com.example.healthytaste.DESSERT_DISH
import com.example.healthytaste.DESSERT_DISH_DETAIL
import com.example.healthytaste.FIRST_DISH
import com.example.healthytaste.FIRST_DISH_DETAIL
import com.example.healthytaste.SECOND_DISH
import com.example.healthytaste.SECOND_DISH_DETAIL
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.DessertDishResponse
import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse
import com.example.healthytaste.model.SecondDish
import com.example.healthytaste.model.SecondDishResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HealthyTasteService {
    @GET(FIRST_DISH)
    suspend fun getFirstDish():FirstDishResponse

    @GET(FIRST_DISH_DETAIL)
    suspend fun getOneFirstDish(@Path("firstDishId")firstDishId: Int): First

    @GET(SECOND_DISH)
    suspend fun getSecondDish():SecondDishResponse

    @GET(SECOND_DISH_DETAIL)
    suspend fun getSecondDetail(@Path("secondDishId")secondDishId:Int): SecondDish
    @GET(DESSERT_DISH)
    suspend fun getDessertDish(): DessertDishResponse
    @GET(DESSERT_DISH_DETAIL)
    suspend fun getDessertDishDetail(@Path("dessertDishId")dessertDishId: Int): DessertDish


}