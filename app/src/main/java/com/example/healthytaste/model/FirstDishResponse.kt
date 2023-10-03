package com.example.healthytaste.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class FirstDishResponse: ArrayList<First>()
@Keep
data class First(
    val id: Long,
    val detalles: Detalles,
    val image: String,
    val nombre: String
)

@Keep
data class Detalles(
    val apto: String,
    val elaboracion: String,
    val ingredientes: List<String>,
    @SerializedName("url_video") val urlVideo: String
)
