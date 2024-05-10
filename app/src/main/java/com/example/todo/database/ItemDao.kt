package com.example.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo.model.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)


    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM ITEMS ORDER BY id DESC")
    fun getAllItems():LiveData<List<Item>>

    @Query("SELECT * FROM ITEMS WHERE Title LIKE :query OR `Desc` Like :query")
    fun getSearchItem(query: String?):LiveData<List<Item>>



}