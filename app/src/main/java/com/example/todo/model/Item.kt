package com.example.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "items")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title:String,
    val Desc:String
):Parcelable
