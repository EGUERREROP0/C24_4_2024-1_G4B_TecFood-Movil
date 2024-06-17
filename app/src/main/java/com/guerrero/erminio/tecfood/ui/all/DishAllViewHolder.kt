package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.TermsActivity
import com.guerrero.erminio.tecfood.databinding.FragmentOrderBinding
import com.guerrero.erminio.tecfood.databinding.ItemDishBinding
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import com.squareup.picasso.Picasso

class DishAllViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemDishBinding.bind(view)

    fun bind(dishItemAllResponse: Category) {

        binding.tvDishName.text = dishItemAllResponse.name
        binding.tvDishPrice.text = dishItemAllResponse.price.toString()

        Picasso.get()
            // .load(dishItemAllResponse.getPokemonImage())
            .load(dishItemAllResponse.imgUrl)
            .into(binding.ivDish)

        binding.root.setOnClickListener {
            val context = it.context
            val intent = Intent(
                context, TermsActivity::class.java
            )
            context.startActivity(intent)
        }
    }
}


