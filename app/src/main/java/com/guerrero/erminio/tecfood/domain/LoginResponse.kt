package com.guerrero.erminio.tecfood.domain

data class LoginResponse(
    val token: String,
    val user: Data,
    val message: String
)
