package com.guerrero.erminio.tecfood.ui.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.data.model.Category

class DishAllAdapter(var dishAllList: List<Category> = emptyList(),
     private val onItemSelected:(Int) -> Unit):
    RecyclerView.Adapter<DishAllViewHolder>(){

    fun updateAllList(dishAllList: List<Category>){
        this.dishAllList = dishAllList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishAllViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DishAllViewHolder(inflater.inflate(R.layout.item_dish, parent, false))
    }

    override fun onBindViewHolder(holder: DishAllViewHolder, position: Int) {
        val item = dishAllList[position]
        holder.bind(item, onItemSelected)
    }

    override fun getItemCount(): Int =  dishAllList.size
}