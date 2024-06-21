package com.guerrero.erminio.tecfood.ui.login

data class LoginResponse(
    val token: String,
    val user: Data,
    val message: String
)

data class Data(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val dni: String,
    val role: Role

)
data class Role(
    val id: Int,
    val name: String
)
