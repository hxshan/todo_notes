package com.example.todo.repository

import com.example.todo.database.ItemDatabase
import com.example.todo.model.Item

class ItemRepository(private val db:ItemDatabase) {

    suspend fun insertItem(item:Item)=db.getItemDao().insertItem(item)
    suspend fun updateItem(item:Item)=db.getItemDao().updateItem(item)
    suspend fun deleteItem(item:Item)=db.getItemDao().deleteItem(item)

    fun getAllItems()=db.getItemDao().getAllItems()

    fun getSearchItem(query:String?)=db.getItemDao().getSearchItem(query)



}