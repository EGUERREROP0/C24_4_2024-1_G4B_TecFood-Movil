package com.guerrero.erminio.tecfood.domain

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


/*
*     "user": {
        "id": 1,
        "firstName": "Test",
        "lastName": "User",
        "phoneNumber": "923456780",
        "email": "erminio@tecsup.edu.pe",
        "password": "$2a$10$UeOM7anzadOBCY/h4ynlC./jPxDLHxl8gYHgbMu3tuo9riaVEaLAm",
        "dni": "12345678",
        "imgUrl": null
        *
        * "role": {
            "id": 2,
            "name": "ROLE_USER"
        },
* */