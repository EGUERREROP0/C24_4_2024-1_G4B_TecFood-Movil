package com.guerrero.erminio.tecfood.ui.search

data class ResponseDish(
    val dish: DishItemResponse,
    val message: String
)
data class DishItemResponse(
    val category: Category,
    val description: String,
    val idDish: Int,
    val imgUrl: String,
    val name: String,
    val price: Double,
    val stock: Int
)
data class Category(
    val idDishCategory: Int,
    val name: String
)




