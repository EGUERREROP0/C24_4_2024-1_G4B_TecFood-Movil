package com.guerrero.erminio.tecfood.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.databinding.ItemDishBinding
import com.squareup.picasso.Picasso

class DishViewHolder(view: View): RecyclerView.ViewHolder(view){
    //Binding
    private val binding = ItemDishBinding.bind(view)

    fun bind(dishItemResponse: DishItemResponse, onItemSelected: (Int) -> Unit){

        binding.tvDishName.text = dishItemResponse.name
        binding.tvDishApparence.text = dishItemResponse.category.name
        binding.tvDishPrice.text = dishItemResponse.price.toString()

        //Picasso para la imagen
        Picasso.get()
            .load(dishItemResponse.imgUrl)
            .into(binding.ivDish)

        //Navegacion hacia detalle
        binding.root.setOnClickListener { onItemSelected(dishItemResponse.idDish) }
    }
}