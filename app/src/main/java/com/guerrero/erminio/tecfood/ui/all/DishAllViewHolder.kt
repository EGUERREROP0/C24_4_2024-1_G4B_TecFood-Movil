package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.TermsActivity
import com.guerrero.erminio.tecfood.data.model.Category
import com.guerrero.erminio.tecfood.databinding.ItemDishBinding
import com.squareup.picasso.Picasso

class DishAllViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemDishBinding.bind(view)

    fun bind(dishItemAllResponse: Category, onItemSelected: (Int) -> Unit){

        binding.tvDishName.text = dishItemAllResponse.name
        binding.tvDishPrice.text = dishItemAllResponse.price.toString()

        Picasso.get()
            // .load(dishItemAllResponse.getPokemonImage())
            .load(dishItemAllResponse.imgUrl)
            .into(binding.ivDish)

        //Navegacion hacia detalle
        binding.root.setOnClickListener { onItemSelected(dishItemAllResponse.idDish) }
    }
}


