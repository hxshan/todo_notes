package com.example.todo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.repository.ItemRepository

class ItemViewModelFactory(val app:Application,private val itemRepository: ItemRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemViewModel(app,itemRepository) as T
    }

}