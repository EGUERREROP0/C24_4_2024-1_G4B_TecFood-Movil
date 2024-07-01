package com.guerrero.erminio.tecfood.data.network

import com.guerrero.erminio.tecfood.data.model.CartResponse
import com.guerrero.erminio.tecfood.data.model.DishInformationDetail
import com.guerrero.erminio.tecfood.data.model.ResponseAllDish
import com.guerrero.erminio.tecfood.ui.History.OrderResponse
import com.guerrero.erminio.tecfood.ui.all.OrderRequest
import com.guerrero.erminio.tecfood.ui.search.ResponseDish
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //http://localhost:4000/api/dish/name/{{name}}

    /*@GET("99c980fdccf958f2706484aab4eb351c/search/{name}")

    suspend fun getDish(@Path("name") dishName: String): Response<ResponseDish>*/
    @GET("api/dish/name/{name}")
    suspend fun getDish(@Path("name") dishName: String): Response<ResponseDish>

    @GET("api/dish")
    suspend fun getPokemon(@Query("limit") limit: Int): Response<ResponseAllDish>

    @GET("api/dish/{id}")
    suspend fun getDishDetail(@Path("id") dishId: Int): Response<DishInformationDetail>

    @POST("/api/cart-dish")
    suspend fun addProductToOrder(@Header("Authorization") token: String, @Body orderRequest: OrderRequest ): Response<Unit>

    @GET("api/cart-dish/user")
    suspend fun getCart(@Header("Authorization") token: String): Response<CartResponse>


    @POST("/api/order-dish")
   suspend fun createOrder(@Header("Authorization") token: String, @Body userIdRequest: UserIdRequest): Response<Unit>

    data class UserIdRequest(val userId: Int)

    @GET("api/order-dish/user")
    suspend fun getUserOrders(
        @Header("Authorization") token: String,
        @Query("status") status: List<String>
    ): Response<OrderResponse>

    @DELETE("api/cart-dish/{dishId}")
    suspend fun deleteCartItem(
        @Header("Authorization") token: String,
        @Path("dishId") dishId: Int
    ): Response<Unit>
}