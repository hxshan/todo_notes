package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.model.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase:RoomDatabase() {

    abstract fun getItemDao():ItemDao

    companion object{
        @Volatile
        private  var instance:ItemDatabase?=null
        private val LOCK =Any()

        operator fun invoke(context: Context)= instance?:
        synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance=it
            }
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java,
                "item_db"
            ).build()

    }

}