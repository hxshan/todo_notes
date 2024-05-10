package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.database.ItemDatabase
import com.example.todo.repository.ItemRepository
import com.example.todo.viewmodel.ItemViewModel
import com.example.todo.viewmodel.ItemViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var itemViewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setupViewModel(){
        val itemRepository=ItemRepository(ItemDatabase(this))
        val viewModelProviderFactory = ItemViewModelFactory(application,itemRepository)
        itemViewModel= ViewModelProvider(this,viewModelProviderFactory)[itemViewModel::class.java]
    }
}