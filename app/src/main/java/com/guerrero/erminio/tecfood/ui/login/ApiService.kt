package com.guerrero.erminio.tecfood.ui.login

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)

interface ApiService {

    @POST("api/auth/login")
    fun postLogin(@Body request: LoginRequest): Call<LoginResponse>

    companion object {
        private const val BASE_URL = "http://192.168.0.102:4000/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}