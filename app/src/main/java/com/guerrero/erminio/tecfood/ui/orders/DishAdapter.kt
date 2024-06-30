package com.guerrero.erminio.tecfood.ui.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.data.model.Cart
import com.squareup.picasso.Picasso

class DishAdapter(private var carts: List<Cart>) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvOrderName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val dishImageView: ImageView = itemView.findViewById(R.id.ivOrderImage)
        val quantityView: TextView = itemView.findViewById(R.id.tvOrderQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish_cart, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val cart = carts[position]
        val dish = cart.dish
        val totalDishPrice = dish.price * cart.quantity

        holder.nameTextView.text = dish.name
        holder.priceTextView.text = totalDishPrice.toString()
        holder.quantityView.text = ": ${cart.quantity}"
        Picasso.get().load(dish.imgUrl).into(holder.dishImageView)


    }

    override fun getItemCount(): Int = carts.size

    fun updateCarts(newCarts: List<Cart>) {
        carts = newCarts
        notifyDataSetChanged()
    }
}
