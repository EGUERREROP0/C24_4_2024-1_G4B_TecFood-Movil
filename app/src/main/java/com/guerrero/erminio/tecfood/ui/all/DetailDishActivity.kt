package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guerrero.erminio.tecfood.data.ApiService
import com.guerrero.erminio.tecfood.data.model.DishInformationDetail
import com.guerrero.erminio.tecfood.databinding.ActivityDetailDishBinding
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import com.guerrero.erminio.tecfood.ui.orders.OrderFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailDishActivity : AppCompatActivity() {
    companion object {
        const val DISH_ID = "dish_id"
    }

    private lateinit var binding: ActivityDetailDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.getIntExtra(DISH_ID, 0)
        getInformation(id)

        initUI()
    }

    private fun initUI() {
        returnArrow()
        goOrders()
    }

    //Retornat flecha -volver al menu principal
    private fun returnArrow(){
        binding.ivreturn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //Navegar a orderFragment
    private fun goOrders(){
        binding.lygoToOrders.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply{
                putExtra("openOrderFragment", true)
            }
            startActivity(intent)
        }
    }

    //Obtener informacion de un plato
    private fun getInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val dishDetail = getRetrifit().create(ApiService::class.java).getDishDetail(id)

            if (dishDetail.body() != null) {
                runOnUiThread{
                    createUI(dishDetail.body()!!)
                }
            }
        }
    }

    private fun createUI(dish: DishInformationDetail){
        binding.tvDishName.text = dish.dish.name
        binding.tvDishNameTop.text = dish.dish.name
        binding.tvDescription.text = dish.dish.description
        binding.tvPrice.text = dish.dish.price.toString()
        binding.tvCategory.text = dish.dish.category.name

        //Cargar imagen
        Picasso.get().load(dish.dish.imgUrl ).into(binding.imageView)
    }

    private fun getRetrifit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.0.102:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}