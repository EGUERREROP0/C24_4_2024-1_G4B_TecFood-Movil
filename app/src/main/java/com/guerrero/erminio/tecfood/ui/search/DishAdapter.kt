package com.guerrero.erminio.tecfood.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.R

/*
class DishAdapter(var dishList: List<DishItemResponse> = emptyList()):
    RecyclerView.Adapter<DishViewHolder>() {

    fun updateList(dishList: List<DishItemResponse>) {
        this.dishList = dishList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //Retornamos valores de DishViewHolder y el item_dish
        return DishViewHolder(inflater.inflate(R.layout.item_dish, parent, false))

    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item = dishList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dishList.size

}*/

class DishAdapter(var dishItem: DishItemResponse? = null,
                  private val onItemSelected:(Int) -> Unit):
    RecyclerView.Adapter<DishViewHolder>() {

    fun updateList(dishItem: DishItemResponse) {
        this.dishItem = dishItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //Retornamos valores de DishViewHolder y el item_dish
        return DishViewHolder(inflater.inflate(R.layout.item_dish, parent, false))

    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        dishItem?.let {
            holder.bind(it, onItemSelected)
        }
    }

    override fun getItemCount(): Int = if (dishItem != null) 1 else 0

}

