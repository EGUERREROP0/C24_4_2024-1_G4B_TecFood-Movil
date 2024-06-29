package com.guerrero.erminio.tecfood.ui.orders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.btnContinueToPay.setOnClickListener{
            val intent = Intent(context, PaymentActivity::class.java)
            startActivity(intent)
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
                        adapter.updateCarts(cartResponse.cart)  // Actualiza el RecyclerView
                        calculateAndDisplayTotal(cartResponse)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    // Maneja el caso en que la respuesta no es exitosa
                    // ...
                }
            }
        }
    }

    private fun calculateAndDisplayTotal(cartResponse: CartResponse) {
        val formattedTotal = String.format("%.2f", cartResponse.totalPayment)
        binding.floatTotal.text = getString(R.string.total_price, formattedTotal)
    }
}
