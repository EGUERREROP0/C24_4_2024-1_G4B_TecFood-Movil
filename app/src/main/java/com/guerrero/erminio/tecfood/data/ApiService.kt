package com.guerrero.erminio.tecfood.data

import com.guerrero.erminio.tecfood.ui.search.DishItemResponse
import com.guerrero.erminio.tecfood.ui.search.ResponseDish
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("99c980fdccf958f2706484aab4eb351c/search/{name}")

    suspend fun getDish(@Path("name") dishName: String): Response<ResponseDish>
}