package com.example.shoppinglistapp.other

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.db.entities.ShoppingItem
import com.example.shoppinglistapp.ui.shoppinglist.ShoppingViewModel

class ShoppingItemAdapter(
    var shoppingItems: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvShoppingItemName = itemView.findViewById<TextView>(R.id.tvItemName)
        val tvShoppingItemAmount = itemView.findViewById<TextView>(R.id.tvItemAmount)

        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val ivPlus: ImageView = itemView.findViewById(R.id.ivPlus)
        val ivMinus: ImageView = itemView.findViewById(R.id.ivMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentShoppingItem = shoppingItems[position]
        holder.tvShoppingItemName.text = currentShoppingItem.name
        holder.tvShoppingItemAmount.text = currentShoppingItem.amount.toString()

        holder.ivDelete.setOnClickListener {

            viewModel.delete(currentShoppingItem)
        }

        holder.ivPlus.setOnClickListener {
            currentShoppingItem.amount++
            viewModel.upsert(currentShoppingItem)
        }

        holder.ivMinus.setOnClickListener {
            if (currentShoppingItem.amount > 0){
                currentShoppingItem.amount--
            } else {
                currentShoppingItem.amount = 0
            }
            viewModel.upsert(currentShoppingItem)
        }
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }


}