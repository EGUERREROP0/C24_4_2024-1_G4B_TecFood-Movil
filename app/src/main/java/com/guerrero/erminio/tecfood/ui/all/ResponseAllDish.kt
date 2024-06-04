package com.guerrero.erminio.tecfood.ui.all

data class ResponseAllDish(

    val categories: List<Category>

)
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String
)
