package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import com.example.todo.databinding.ItemLayoutBinding
import com.example.todo.fragments.HomeFragment
import com.example.todo.fragments.HomeFragmentDirections
import com.example.todo.model.Item

class ItemAdapter:RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val itemBinding: ItemLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object  : DiffUtil.ItemCallback<Item>(){

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.Desc==newItem.Desc
                    && oldItem.Title == newItem.Title
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current= differ.currentList[position]

        holder.itemBinding.itemTitle.text=current.Title
        holder.itemBinding.itemDesc.text=current.Desc

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditFragment(current)
            it.findNavController().navigate(direction)
        }
    }

}