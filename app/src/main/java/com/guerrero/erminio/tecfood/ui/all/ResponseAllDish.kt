package com.guerrero.erminio.tecfood.ui.all

import com.google.gson.annotations.SerializedName

data class ResponseAllDish(
    @SerializedName("dishes")
    val categories: List< Category>

   // val categories: List<Category>

)
data class Category(
    //val category: Category,
    val description: String,
    val idDish: Int,
    val imgUrl: String,
    val name: String,
    val price: Double,
    val stock: Int

//    val idCategory: String,
//    val strCategory: String,
//    val strCategoryThumb: String
)
/*


 */