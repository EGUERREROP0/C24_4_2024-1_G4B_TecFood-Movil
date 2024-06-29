package com.guerrero.erminio.tecfood.ui.orders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guerrero.erminio.tecfood.databinding.ActivityPruebaBinding

class PruebaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPruebaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
