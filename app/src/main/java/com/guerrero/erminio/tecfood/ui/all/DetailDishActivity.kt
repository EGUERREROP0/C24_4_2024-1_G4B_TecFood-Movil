package com.guerrero.erminio.tecfood.ui.all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.model.DishInformationDetail
import com.guerrero.erminio.tecfood.data.network.RetrofitInstance
import com.guerrero.erminio.tecfood.databinding.ActivityDetailDishBinding
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.withContext


val Context.dataStore by preferencesDataStore(name = "DATA_DISH")

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

        // Initializo Retrofit
        retrofit = RetrofitInstance.getRetrofit

        val id: Int = intent.getIntExtra(DISH_ID, 0)
        getInformation(id)

        initUI()

        /*
        * ===========SAVE VALUES  DATASTORE=========
        * */
        /*val nameDish = binding.tvDishName
        val priceDish = binding.tvPrice
        val imageDish = binding.imageView

        binding.btnAddCart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveValuesDataStore(nameDish.text.toString(), priceDish.text.toString(), imageDish)

            }
        }*/
    }

    private fun initUI() {
        returnArrow()
        goOrders()

    }

    //Obtener informacion de un plato
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

        // Cargar imagen
        val imageUrl = dish.dish.imgUrl
        Picasso.get().load(imageUrl).into(binding.imageView)

        // Guardar valores en DataStore cuando se hace clic en el botón
        binding.btnAddCart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveValuesDataStore(binding.tvDishName.text.toString(), binding.tvPrice.text.toString(), imageUrl)
                withContext(Dispatchers.Main) {
                    //Mensaje de agregado al carrrito
                    addCart()
                }
            }
        }
    }

    //Retornat flecha -volver al menu principal
    private fun returnArrow() {
        binding.ivreturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //Navegar a orderFragment
    private fun goOrders() {
        binding.lygoToOrders.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("openOrderFragment", true)
            }
            startActivity(intent)
        }
    }

    //Click en agregar al carrito
    private fun addCart() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirmacion")
            .setMessage("\uD83C\uDF89 ¡Genial! El plato ha sido agregado al carrito, verifica tus ordenes. \uD83D\uDED2")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    //AGREGA DATOS EN DATASTORE
    private suspend fun saveValuesDataStore(name: String, price: String, image: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("nameDish")] = name
            preferences[stringPreferencesKey("priceDish")] = price
            preferences[stringPreferencesKey("imageDish")] = image
        }
        Log.d("DataStore", "Saved to DataStore: Name: $name, Price: $price, Image: $image")
    }

}