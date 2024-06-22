package com.guerrero.erminio.tecfood.ui.orders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.FragmentAllBinding
import com.guerrero.erminio.tecfood.databinding.FragmentOrderBinding
import com.guerrero.erminio.tecfood.ui.all.DishDataStore
import com.guerrero.erminio.tecfood.ui.all.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderFragment : Fragment() {

    private  var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DishAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        binding.btnContinueToPay.setOnClickListener{
            val intent = Intent(context, PruebaActivity::class.java)
            startActivity(intent)
        }

        adapter = DishAdapter(emptyList())
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            getValuesDataStore().collect { dataStoreValues ->
                withContext(Dispatchers.Main) {
                    adapter.updateDishes(dataStoreValues)
                    Log.d("DataStore1", "Dishes: $dataStoreValues")
                }
            }
        }


        return binding.root
    }

    private fun getValuesDataStore() = requireActivity().dataStore.data.map { preferences ->
        listOf(
            DishDataStore(
                nameDish = preferences[stringPreferencesKey("nameDish")].orEmpty(),
                priceDish = preferences[stringPreferencesKey("priceDish")].orEmpty(),
                imageDish = preferences[stringPreferencesKey("imageDish")].orEmpty()
            )
        )
    }

}