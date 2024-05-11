package com.example.todo.fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.databinding.FragmentEditBinding
import com.example.todo.model.Item
import com.example.todo.viewmodel.ItemViewModel

class EditFragment : Fragment(R.layout.fragment_edit),MenuProvider {

    private var editItemBinding:FragmentEditBinding?=null
    private val binding get() = editItemBinding!!

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var currentItem:Item

    private val args:EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        editItemBinding=FragmentEditBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
        itemViewModel=(activity as MainActivity).itemViewModel
        currentItem=args.item!!

        binding.editItemTitle.setText(currentItem.Title)
        binding.editItemDesc.setText(currentItem.Desc)

        binding.editItemFab.setOnClickListener{
            val itemTitle=binding.editItemTitle.text.toString().trim()
            val itemDesc=binding.editItemDesc.text.toString().trim()

            if(itemTitle.isNotEmpty()){
                val item = Item(currentItem.id,itemTitle,itemDesc)
                itemViewModel.editItem(item)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context,"Please enter a title",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun deleteItem(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Item")
            setMessage("Do you want to delete this task")
            setPositiveButton("Delete"){_,_->
                itemViewModel.deleteItem(currentItem)
                Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu ->{
                deleteItem()
                true
            }else -> false

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editItemBinding=null
    }
}