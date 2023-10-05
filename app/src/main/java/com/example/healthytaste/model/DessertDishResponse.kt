package com.example.healthytaste.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class DessertDishResponse: ArrayList<DessertDish>()
@Keep
data class DessertDish(
    val id: Long,
    val details: DessertDishDetails,
    val image: String,
    val name: String
)

@Keep
data class DessertDishDetails(
    val apto: String,
    val elaboration: String,
    val ingredients: List<String>,
    @SerializedName("url_video") val urlVideo: String
)
