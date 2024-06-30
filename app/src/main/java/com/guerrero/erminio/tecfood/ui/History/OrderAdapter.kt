package com.guerrero.erminio.tecfood.ui.History

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.ItemOrderBinding


class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.tvOrderId.text = "Orden: ${order.id}"
        holder.binding.tvOrderName.text = "${order.orderDate }"
        holder.binding.tvOrderStatus.text = "Estado: ${order.status}"
        holder.binding.tvOrderTotal.text = "Total: $${order.total}"
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun updateOrders(newOrders: List<Order>) {
        this.orders = newOrders
        notifyDataSetChanged()
    }
}
