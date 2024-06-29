package com.guerrero.erminio.tecfood.data.model

data class CartResponse(
    val message: String,
    val cart: List<Cart>,
    val totalQuantity: Int,
    val totalPayment: Double
)

data class Cart(
    val idCart: Int,
    val quantity: Int,
    val dish: Dish
)

data class Dish(
    val idDish: Int,
    val name: String,
    val imgUrl: String,
    val stock: Int,
    val description: String,
    val price: Double,
   // val category: Category
)

//data class Category(
//    val idDishCategory: Int,
//    val name: String
//)
