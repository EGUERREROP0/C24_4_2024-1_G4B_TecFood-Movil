package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.os.Bundle

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
    }

    private fun initUI() {
        returnArrow()
        goOrders()
        addCart()
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

        //Cargar imagen
        Picasso.get().load(dish.dish.imgUrl).into(binding.imageView)
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
        binding.btnAddCart.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Confirmacion")
                .setMessage("\uD83C\uDF89 ¡Genial! El plato ha sido agregado al carrito, verifica tus ordenes. \uD83D\uDED2")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.setOnDismissListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            dialog.show()
        }
    }

}