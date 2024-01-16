package com.example.healthytaste.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class FirstDishResponse : ArrayList<First>()

@Keep
data class First(
    val id: Long,
    val details: FirstDishDetails,
    val image: String,
    val name: String,
    val numPersons: Int
)

@Keep
data class FirstDishDetails(
    @SerializedName("img_allergies") val allergies: String,
    val elaboration: String,
    val ingredients: List<String>,
    @SerializedName("url_video") val urlVideo: String,
    val calories: Double,
    val fats: Double,
    val protein: Double,
    val sugar: Double
)
