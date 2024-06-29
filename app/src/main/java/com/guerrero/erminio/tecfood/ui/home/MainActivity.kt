package com.guerrero.erminio.tecfood.ui.home


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.ActivityMainBinding
import com.guerrero.erminio.tecfood.ui.orders.OrderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Snackbar.make(binding.root, "Bienvenido", Snackbar.LENGTH_SHORT).show()
        //Iniciando navegacion
        initUI()

        if (intent.getBooleanExtra("openOrderFragment", false)) {
            navigateToOrderFragment()
        }

    }

    private fun initUI() {
        initNavigation()
    }

    //Agregando framentes -- al main Activity
    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fcView) as NavHostFragment

        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    fun navigateToOrderFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcView, OrderFragment())
            .commit()
    }

}