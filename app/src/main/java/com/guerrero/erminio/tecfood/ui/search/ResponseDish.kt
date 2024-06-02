package com.guerrero.erminio.tecfood.ui.search

import com.google.gson.annotations.SerializedName

data class ResponseDish(
    @SerializedName("response")
    val response: String,

    @SerializedName("results-for")
    val resultsFor: String,

    @SerializedName("results")
    val results: List<DishItemResponse>

)

data class DishItemResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: ImageDish,
    @SerializedName("appearance") val appearance: AparenceDish,
)

data class ImageDish(
    @SerializedName("url") val url: String
)

data class AparenceDish(
    @SerializedName("race") val race: String,
)
