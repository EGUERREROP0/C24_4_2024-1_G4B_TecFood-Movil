package com.guerrero.erminio.tecfood.domain

data class search(
    val dish: Dish,
    val message: String
)

data class Dish(
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