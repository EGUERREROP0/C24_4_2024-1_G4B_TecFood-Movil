package com.guerrero.erminio.tecfood.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.network.RetrofitInstance
import com.guerrero.erminio.tecfood.databinding.FragmentSearchBinding
import com.guerrero.erminio.tecfood.ui.all.DetailDishActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var retrofit: Retrofit

    //Inicializar Adapter
    private lateinit var adapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        //Inicializando retrofit global
        retrofit = RetrofitInstance.getRetrofit
        //Inicilizando
        initUI()

        return  binding.root

    }

    private fun initUI() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())

                return false
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        //Crear adapter
        adapter = DishAdapter { navegateToDetailActivity(it) }
        binding.rvListDishes.setHasFixedSize(true)
        binding.rvListDishes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListDishes.adapter = adapter
    }

    //Buscar por nombre
    private fun searchByName(query: String) {
        //Cargar progresBar
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit
                .create(ApiService::class.java)
                .getDish(query)

            if (myResponse.isSuccessful) {
                Log.i("Response", "verified")

                val response: ResponseDish? = myResponse.body()
                if (response != null) {
                    Log.i("Response", response.toString())

                    requireActivity().runOnUiThread {
                        binding.progressBar.isVisible = false
                        adapter.updateList(response.dish)
                    }
                } else {
                    Log.i("Response", "Error")
                }

            } else {
                Log.i("Response", "Error")
            }
        }

    }

    private fun navegateToDetailActivity(id: Int){
        var intent = Intent(requireContext(), DetailDishActivity::class.java)
        intent.putExtra(DetailDishActivity.DISH_ID, id)
        startActivity(intent)
    }

}