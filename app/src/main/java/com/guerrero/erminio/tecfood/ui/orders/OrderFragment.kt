package com.guerrero.erminio.tecfood.ui.orders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.data.model.CartResponse
import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.databinding.FragmentOrderBinding
import com.guerrero.erminio.tecfood.ui.pay.PaymentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONObject

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DishAdapter
    private lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        binding.btnContinueToPay.setOnClickListener {
            continueToPay()
        }

        adapter = DishAdapter(emptyList())
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter

        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.102:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        getCart()  // Llamar a la función para obtener el carrito

        return binding.root
    }

    private fun getCart() {
        val sharedPreferences = PreferenceHelper.defaultPrefs(requireContext())
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            // Maneja el caso en que el token es nulo
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val apiService = retrofit.create(ApiService::class.java)
            val response = apiService.getCart("Bearer $token")
            if (response.isSuccessful) {
                val cartResponse = response.body()
                if (cartResponse != null) {
                    withContext(Dispatchers.Main) {
                        adapter.updateCarts(cartResponse.cart)
                        calculateAndDisplayTotal(cartResponse)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    // Maneja el caso en que la respuesta no es exitosa
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val errorMessage = jsonObject.getString("error")
                    Log.e("OrderFragment", "Error: $errorMessage")
                }
            }
        }
    }

    private fun calculateAndDisplayTotal(cartResponse: CartResponse) {
        val formattedTotal = String.format("%.2f", cartResponse.totalPayment)
        binding.floatTotal.text = getString(R.string.total_price, formattedTotal)
    }

    private fun continueToPay() {
        val sharedPreferences = PreferenceHelper.defaultPrefs(requireContext())
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            // Maneja el caso en que el token es nulo
            return
        }
        val userId = getUserIdFromToken(token) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val apiService = retrofit.create(ApiService::class.java)
            val userIdRequest = ApiService.UserIdRequest(userId)
            val response = apiService.createOrder("Bearer $token", userIdRequest)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Order created", Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody)
                    val errorMessage = jsonObject.getString("error")
                    Log.e("OrderFragment", "Error: $errorMessage")
                }
            }
        }
    }

    private fun getUserIdFromToken(token: String): Int? {
        return 123
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
