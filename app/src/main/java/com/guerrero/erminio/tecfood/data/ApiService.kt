package com.guerrero.erminio.tecfood.data

import com.guerrero.erminio.tecfood.data.model.DishInformationDetail
import com.guerrero.erminio.tecfood.data.model.ResponseAllDish
import com.guerrero.erminio.tecfood.ui.search.ResponseDish
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("99c980fdccf958f2706484aab4eb351c/search/{name}")

    suspend fun getDish(@Path("name") dishName: String): Response<ResponseDish>

    @GET("api/dish")
    suspend fun getPokemon(@Query("limit") limit: Int): Response<ResponseAllDish>

    @GET("api/dish/{id}")
    suspend fun getDishDetail(@Path("id") dishId: Int): Response<DishInformationDetail>
}