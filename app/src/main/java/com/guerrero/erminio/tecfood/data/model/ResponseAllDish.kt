package com.guerrero.erminio.tecfood.data.model

import com.google.gson.annotations.SerializedName

data class ResponseAllDish(
    @SerializedName("dishes")
    val categories: List<Category>

   // val categories: List<Category>

)
data class Category(
    //@SerializedName("Category")
    val category: CategoryDish,
    val description: String,
    val idDish: Int,
    val imgUrl: String,
    val name: String,
    val price: Double,
    val stock: Int

)
data class CategoryDish(
    val idDishCategory: Int,
    val name: String
)
/*


 */