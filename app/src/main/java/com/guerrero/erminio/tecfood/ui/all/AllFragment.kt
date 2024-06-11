package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.guerrero.erminio.tecfood.LoginActivity
import com.guerrero.erminio.tecfood.LoginActivity.Companion.useremail
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.TermsActivity
import com.guerrero.erminio.tecfood.data.ApiService

import com.guerrero.erminio.tecfood.databinding.FragmentAllBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AllFragment : Fragment()/*, NavigationView.OnNavigationItemSelectedListener*/ {

    private lateinit var drawer: DrawerLayout
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!

    private lateinit var retrofit: Retrofit
    private lateinit var adapter: DishAllAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)

        // Initialize Retrofit and adapter here
        retrofit = getRetrifit()
        adapter = DishAllAdapter()

        // Set the RecyclerView's adapter here
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = adapter

        initUI()

//        initToolbar()
//        initNavigationView()

        return binding.root

    }

    private fun initUI() {
        getAllDishes()
    }

    private fun getAllDishes() {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val request = retrofit.create(ApiService::class.java).getPokemon(100)
            if (request.isSuccessful) {
                request.body()?.let {
                    requireActivity().runOnUiThread {
                        adapter.updateAllList(it.categories)
                        binding.progressBar.isVisible = false
                    }
                }
                Log.i("yo",  request.body().toString())
                //Log.d("yo", "Funciona")
            }else{
                Log.i("yo", "No Funciona")
            }
        }
    }

    private fun getRetrifit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }






   /* private fun initToolbar() {
        //enableEdgeToEdge()
//        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.toolbar_main)
//        setSupportActionBar(toolbar)

        val toolbar: androidx.appcompat.widget.Toolbar =  binding.
        (activity as AppCompatActivity).setSupportActionBar(toolbar)


        //Variables globales
        drawer = binding.drawerLayout  // findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            activity, drawer, toolbar, R.string.bar_title,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }


    //Function to inicialize the navigation view
    private fun initNavigationView() {
        var navigationView: NavigationView = binding.navView  //findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var headerView: View =
            LayoutInflater.from(context).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        var tvUser = headerView.findViewById<TextView>(R.id.tvUser)
        tvUser.text = useremail

    }

    // dar vida al menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_signOut -> {

            }

            R.id.nav_item_record -> {
                histories()
            }

            R.id.nav_item_searchDish -> {
                searchDish()
            }

            R.id.nav_item_Main ->{
                val intent = Intent(requireContext(), DishAllActivity::class.java)
                startActivity(intent)
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun histories() {
        val intent = Intent(requireContext(), TermsActivity::class.java)
        startActivity(intent)
    }

    private fun searchDish() {
        val intent = Intent(requireContext(), DishListActivity::class.java)
        startActivity(intent)
    }*/

}


