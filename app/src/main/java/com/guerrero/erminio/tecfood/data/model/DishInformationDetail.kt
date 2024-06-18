package com.guerrero.erminio.tecfood.data.model

import com.google.gson.annotations.SerializedName

data class DishInformationDetail (
    @SerializedName("dish")
    val dish: DishDetail
)

data class DishDetail(
    val description: String,
    val idDish: Int,
    val imgUrl: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val category: Category
)

data class Categoty(
    val name: String,
)