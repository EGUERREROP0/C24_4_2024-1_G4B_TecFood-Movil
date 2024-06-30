package com.guerrero.erminio.tecfood.ui.History


data class OrderResponse(
    val message: String,
    val orderDishes: List<Order>
)
data class Order(
    val id: Int,
    val invoiceReportUrl: String?,
    val orderDate: String,
    val status: String,
    val total: Double,
    val user: User
)

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val dni: String,
    val role: Role
)

data class Role(
    val id: Int,
    val name: String
)
