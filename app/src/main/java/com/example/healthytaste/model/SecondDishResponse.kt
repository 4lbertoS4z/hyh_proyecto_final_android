package com.example.healthytaste.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class SecondDishResponse: ArrayList<SecondDish> ()

@Keep
data class SecondDish(
    val id: Long,
    val details: SecondDishDishDetails,
    val image: String,
    val name: String
)

@Keep
data class SecondDishDishDetails(
    @SerializedName("img_allergies") val allergies: String,
    val elaboration: String,
    val ingredients: List<String>,
    @SerializedName("url_video") val urlVideo: String
)
