package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.guerrero.erminio.tecfood.data.model.DishInformationDetail
import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.data.network.RetrofitInstance
import com.guerrero.erminio.tecfood.databinding.ActivityDetailDishBinding
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

data class OrderRequest(
    val userId: Int,
    val dishId: Int,
    val quantity: Int
)

class DetailDishActivity : AppCompatActivity() {
    companion object {
        const val DISH_ID = "dish_id"
    }

    private lateinit var binding: ActivityDetailDishBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = RetrofitInstance.getRetrofit

        val id: Int = intent.getIntExtra(DISH_ID, 0)
        getInformation(id)
        initUI()
    }

    private fun initUI() {
        returnArrow()
        goOrders()
    }

    private fun getInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val dishDetail = retrofit.create(ApiService::class.java).getDishDetail(id)
            if (dishDetail.body() != null) {
                runOnUiThread {
                    createUI(dishDetail.body()!!)
                }
            }
        }
    }

    private fun createUI(dish: DishInformationDetail) {
        binding.tvDishName.text = dish.dish.name
        binding.tvDishNameTop.text = dish.dish.name
        binding.tvDescription.text = dish.dish.description
        binding.tvPrice.text = dish.dish.price.toString()
        binding.tvCategory.text = dish.dish.category.name

        val idDish = dish.dish.idDish

        // Cargar imagen
        val imageUrl = dish.dish.imgUrl
        Picasso.get().load(imageUrl).into(binding.imageView)

        binding.btnAddCart.setOnClickListener {
            val sharedPreferences = PreferenceHelper.defaultPrefs(this)
            val token = sharedPreferences.getString("token", null) ?: return@setOnClickListener
            val userId = getUserIdFromToken(token)
            val quantity = 1

            CoroutineScope(Dispatchers.IO).launch {
                addProductToOrder(userId, idDish, quantity, token)
            }
        }

    }

    private suspend fun addProductToOrder(userId: Int, dishId: Int, quantity: Int, token: String) {
        try {
            val apiService = retrofit.create(ApiService::class.java)
            val orderRequest = OrderRequest(userId, dishId, quantity)
            val response = apiService.addProductToOrder("Bearer $token", orderRequest)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    addCart()
                    Log.d("DetailDishActivity", "Product added successfully")
                }
            } else {
                withContext(Dispatchers.Main) {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val errorMessage = jsonObject.getString("error")
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                    //Log.e("DetailDishActivity", "Failed to add product. Status code: ${response.code()}, Error body: ${response.errorBody()?.string()}")

                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("DetailDishActivity", "Exception: ${e.message}")
            }
        }
    }

    private fun returnArrow() {
        binding.ivreturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goOrders() {
        binding.lygoToOrders.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("openOrderFragment", true)
            }
            startActivity(intent)
        }
    }

    private fun addCart() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirmacion")
            .setMessage("\uD83C\uDF89 ¡Genial! El plato ha sido agregado al carrito, verifica tus ordenes. \uD83D\uDED2")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getUserIdFromToken(token: String): Int {
        return 123
    }
}

