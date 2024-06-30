package com.guerrero.erminio.tecfood.ui.History



import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.R

import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.databinding.ActivityHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONObject

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: OrderAdapter
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.title = "Historial de órdenes"

        adapter = OrderAdapter(emptyList())
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.102:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        getUserOrders()
    }

    private fun getUserOrders() {
        val sharedPreferences = PreferenceHelper.defaultPrefs(this)
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            // Maneja el caso en que el token es nulo
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val apiService = retrofit.create(ApiService::class.java)
            val response = apiService.getUserOrders("Bearer $token", "PENDING")
            if (response.isSuccessful) {
                val orderResponse = response.body()
                if (orderResponse != null) {
                    withContext(Dispatchers.Main) {
                        adapter.updateOrders(orderResponse.orderDishes)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val errorMessage = jsonObject.getString("error")
                    Log.e("HistoryActivity", "Error: $errorMessage")
                }
            }
        }
    }
}
