package com.guerrero.erminio.tecfood.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.databinding.ActivityPruebaBinding
import com.guerrero.erminio.tecfood.ui.all.DishDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PruebaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPruebaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
