package com.guerrero.erminio.tecfood.ui.all

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.data.ApiService
import com.guerrero.erminio.tecfood.databinding.ActivityDishallBinding
import com.guerrero.erminio.tecfood.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*class DishAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishallBinding

    private lateinit var retrofit: Retrofit
    private lateinit var adapter: DishAllAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrifit()
        initUI()
    }

    private fun initUI() {
        getAllDishes()

        //Crear adapter
        adapter = DishAllAdapter()
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }

    private fun getAllDishes() {
        binding.progressBarScreenMain.isVisible = true

        CoroutineScope(Dispatchers.IO).launch {
            val request = retrofit.create(ApiService::class.java).getPokemon(20)
            if (request.isSuccessful) {
                request.body()?.let {
                    runOnUiThread {
                        adapter.updateAllList(it.categories)
                        binding.progressBarScreenMain.isVisible = false
                    }
                }
                //Log.i("yo", "Funciona")
            }else{
                runOnUiThread{
                    binding.progressBarScreenMain.isVisible = false
                    binding.rvList.isVisible = false

                    binding.errorImage.isVisible = true

                }
                Log.i("yo", "No Funciona")
            }
        }

    }

    private fun getRetrifit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}*/