package com.example.shoppinglistapp.ui.shoppinglist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.db.entities.ShoppingItem

class AddShoppingItemDialog(context: Context, var addDialogListener: AddDialogListener): AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_shopping_item)

        val tvAdd = findViewById<TextView>(R.id.tvAdd)
        val tvCancel = findViewById<TextView>(R.id.tvCancel)
        val etName = findViewById<EditText>(R.id.etShoppingItemName)
        val etAmount = findViewById<EditText>(R.id.etShoppingItemAmount)

        tvAdd?.setOnClickListener {
            val name = etName?.text.toString()
            val amount = etAmount?.text.toString()

            if(name.isEmpty() || amount.isEmpty()){
                Toast.makeText(context, "Please enter all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = ShoppingItem(name, amount.toInt())
            addDialogListener.onAddButtonClicked(item)
            dismiss()
        }

        tvCancel?.setOnClickListener {
            cancel()
        }

    }
}