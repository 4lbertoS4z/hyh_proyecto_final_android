package com.example.healthytaste.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class DessertDishResponse : ArrayList<DessertDish>()

@Keep
data class DessertDish(
    val id: Long,
    val details: DessertDishDetails,
    val image: String,
    val name: String,
    val numPersons: Int
)

@Keep
data class DessertDishDetails(
    @SerializedName("img_allergies") val allergies: String,
    val elaboration: String,
    val ingredients: List<String>,
    @SerializedName("url_video") val urlVideo: String,
    val calories: Double,
    val fats: Double,
    val protein: Double,
    val sugar: Double
)
