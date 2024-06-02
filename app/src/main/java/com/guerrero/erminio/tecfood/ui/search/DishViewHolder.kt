package com.guerrero.erminio.tecfood.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.databinding.ItemDishBinding
import com.squareup.picasso.Picasso

class DishViewHolder(view: View): RecyclerView.ViewHolder(view){
    //Binding
    private val binding = ItemDishBinding.bind(view)

    fun bind(dishItemResponse: DishItemResponse){

        binding.tvDishName.text = dishItemResponse.name
        binding.tvDishApparence.text = dishItemResponse.appearance.race

        //Picasso para laimagen
        Picasso.get()
            .load(dishItemResponse.image.url)
            .into(binding.ivDish)
    }
}