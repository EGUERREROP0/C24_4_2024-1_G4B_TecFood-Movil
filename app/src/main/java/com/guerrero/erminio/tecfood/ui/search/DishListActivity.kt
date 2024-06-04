package com.guerrero.erminio.tecfood.ui.search

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.data.ApiService
import com.guerrero.erminio.tecfood.databinding.ActivityDishlistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DishListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishlistBinding
    private lateinit var retrofit: Retrofit

    //Inicializar Adapter
    private lateinit var adapter: DishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializando retrofit global
        retrofit = getRetrofit()
        //Inicilizando
        initUI()

    }

    private fun initUI() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())

                return false
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        //Crear adapter
        adapter = DishAdapter()
        binding.rvListDishes.setHasFixedSize(true)
        binding.rvListDishes.layoutManager = LinearLayoutManager(this)
        binding.rvListDishes.adapter = adapter
    }

    //Buscar por nombre
    private fun searchByName(query: String) {
        //Cargar progresBar
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit
                .create(ApiService::class.java)
                .getDish(query)

            if (myResponse.isSuccessful) {
                Log.i("Response", "verified")

                val response: ResponseDish? = myResponse.body()
                if (response != null) {
                    Log.i("Response", response.toString())

                    runOnUiThread {
                        binding.progressBar.isVisible = false
                        adapter.updateList(response.results)
                    }
                } else {
                    Log.i("Response", "Error")
                }

            } else {
                Log.i("Response", "Error")
            }
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.superheroapi.com/api.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}