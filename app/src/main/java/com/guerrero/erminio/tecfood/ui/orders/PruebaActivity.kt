package com.guerrero.erminio.tecfood.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.databinding.ActivityPruebaBinding
import com.guerrero.erminio.tecfood.ui.all.DishDataStore
import com.guerrero.erminio.tecfood.ui.all.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PruebaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPruebaBinding
    private lateinit var adapter: DishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DishAdapter(emptyList())
        binding.rvDishes.layoutManager = LinearLayoutManager(this)
        binding.rvDishes.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            getValuesDataStore().collect { dataStoreValues ->
                withContext(Dispatchers.Main) {
                    adapter.updateDishes(dataStoreValues)
                    Log.d("DataStore1", "Dishes: $dataStoreValues")
                }
            }
        }
    }

    private fun getValuesDataStore() = dataStore.data.map { preferences ->
        listOf(
            DishDataStore(
                nameDish = preferences[stringPreferencesKey("nameDish")].orEmpty(),
                priceDish = preferences[stringPreferencesKey("priceDish")].orEmpty(),
                imageDish = preferences[stringPreferencesKey("imageDish")].orEmpty()
            )
        )
    }
}
