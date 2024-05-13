package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.adapter.ItemAdapter
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.model.Item
import com.example.todo.viewmodel.ItemViewModel

class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener,MenuProvider {

    private var homeBinding:FragmentHomeBinding?=null
    private val binding get()=homeBinding!!

    private lateinit var itemViewModel:ItemViewModel
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        itemViewModel =(activity as MainActivity).itemViewModel
        setupHomeRV()

        binding.addItemFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun updateUI(item: List<Item>?){
        if(item!=null){
            if(item.isNotEmpty()){
                binding.emptyItemImage.visibility=View.GONE
                binding.homeRV.visibility=View.VISIBLE
            }else{
                binding.emptyItemImage.visibility=View.VISIBLE
                binding.homeRV.visibility=View.GONE
            }
        }
    }

    private fun  setupHomeRV(){
        itemAdapter= ItemAdapter()
        binding.homeRV.apply {
            //layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = itemAdapter
        }
        activity?.let {
            itemViewModel.getAllItems().observe(viewLifecycleOwner){ item ->
                itemAdapter.differ.submitList(item)
                updateUI(item)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    private fun searchNote(query: String?){
        val searchQuery="%$query"

        itemViewModel.searchItem(searchQuery).observe(this){list ->
            itemAdapter.differ.submitList(list)
        }
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText !=null){
            searchNote(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch=menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled=false
        menuSearch.setOnQueryTextListener(this)
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }

}