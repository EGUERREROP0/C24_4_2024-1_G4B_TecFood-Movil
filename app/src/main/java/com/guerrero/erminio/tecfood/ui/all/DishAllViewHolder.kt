package com.guerrero.erminio.tecfood.ui.all

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.databinding.ItemDishBinding
import com.squareup.picasso.Picasso

class DishAllViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val binding = ItemDishBinding.bind(view)

    fun bind(dishItemAllResponse: Category) {

        binding.tvDishName.text  = dishItemAllResponse.strCategory
        //binding.tvDishApparence.text = dishItemAllResponse.url
        Picasso.get()
            // .load(dishItemAllResponse.getPokemonImage())
            .load(dishItemAllResponse.strCategoryThumb)
            .into(binding.ivDish)
    }
}