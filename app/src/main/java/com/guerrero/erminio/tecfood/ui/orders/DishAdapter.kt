package com.guerrero.erminio.tecfood.ui.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.ui.all.DishDataStore
import com.squareup.picasso.Picasso


class DishAdapter(private var dishes: List<DishDataStore>) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvOrderName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val dishImageView: ImageView = itemView.findViewById((R.id.ivOrderImage))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_cart, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        holder.nameTextView.text = dish.nameDish
        holder.priceTextView.text = dish.priceDish
        Picasso
            .get()
            .load(dish.imageDish)
            .into(holder.dishImageView)
    }

    override fun getItemCount(): Int = dishes.size

    fun updateDishes(newDishes: List<DishDataStore>) {
        dishes = newDishes
        notifyDataSetChanged()
    }
}
