package com.example.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Item
import com.example.todo.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(app:Application,private val itemRepository: ItemRepository):AndroidViewModel(app) {

    fun addItem(item: Item)=
        viewModelScope.launch {
            itemRepository.insertItem(item)
        }
    fun deleteItem(item: Item)=
        viewModelScope.launch {
            itemRepository.deleteItem(item)
        }
    fun editItem(item: Item)=
        viewModelScope.launch {
            itemRepository.updateItem(item)
        }


}