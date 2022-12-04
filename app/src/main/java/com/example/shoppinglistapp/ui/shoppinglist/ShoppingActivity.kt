package com.example.shoppinglistapp.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.data.db.ShoppingDatabase
import com.example.shoppinglistapp.data.db.entities.ShoppingItem
import com.example.shoppinglistapp.data.repositories.ShoppingRepository
import com.example.shoppinglistapp.other.ShoppingItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class ShoppingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
//        asagidakiler yerine kodein kullandik dependency injection icin
//        val database = ShoppingDatabase(this)
//        val repository = ShoppingRepository(database)
//        val factory = ShoppingViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        val rvShoppingItems = findViewById<RecyclerView>(R.id.rvShoppingItems)
        val fabAddShoppingItem = findViewById<FloatingActionButton>(R.id.fabAddShoppingItem)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)
        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.shoppingItems = it
            adapter.notifyDataSetChanged()
        })

        fabAddShoppingItem.setOnClickListener {
            AddShoppingItemDialog(this,
             object: AddDialogListener{
                 override fun onAddButtonClicked(item: ShoppingItem) {
                     viewModel.upsert(item)
                 }
             }).show()
        }
    }
}