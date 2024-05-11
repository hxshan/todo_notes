package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.model.Item
import com.example.todo.viewmodel.ItemViewModel


class AddFragment : Fragment(R.layout.fragment_add),MenuProvider {

    private var addItemBinding:FragmentAddBinding?=null
    private val binding get()= addItemBinding!!

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var addItemView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    addItemBinding=FragmentAddBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        itemViewModel=(activity as MainActivity).itemViewModel
        addItemView=view
    }

    private fun saveItem(view: View){
        val itemTitle=binding.addItemTitle.text.toString().trim()
        val itemDesc = binding.addItemDesc.text.toString().trim()

        if(itemTitle.isNotEmpty()){
            val item = Item(0,itemTitle,itemDesc)
            itemViewModel.addItem(item)

            Toast.makeText(addItemView.context,"New Task Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addItemView.context,"Please enter a title",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveItem(addItemView)
                true
            }
            else ->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addItemBinding=null
    }
}