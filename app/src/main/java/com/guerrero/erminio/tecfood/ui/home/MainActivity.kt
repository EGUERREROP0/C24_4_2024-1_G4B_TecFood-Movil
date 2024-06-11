package com.guerrero.erminio.tecfood.ui.home


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener */ {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciando navegacion
        initUI()

        //Toast.makeText(this, "Welcome to TecFood $useremail", Toast.LENGTH_SHORT).show()

        /*initToolbar()
        initNavigationView()*/

    }


    private fun initUI(){
        initNavigation()
    }

    //Agregando framentes -- al main Activity
    private fun initNavigation(){
        val navHost = supportFragmentManager.findFragmentById(R.id.fcView) as NavHostFragment

        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }


    // Function to initialize the toolbar
   /* private fun initToolbar() {
        //enableEdgeToEdge()
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        //Variables globales
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.bar_title,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    //Function to inicialize the navigation view
    private fun initNavigationView() {
        var navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var headerView: View =
            LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        var tvUser = headerView.findViewById<TextView>(R.id.tvUser)
        tvUser.text = useremail

    }


    fun callSignOut() {
        signOut()
    }

    private fun signOut() {
        useremail = ""
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    // dar vida al menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_signOut -> {
                signOut()
            }

            R.id.nav_item_record -> {
                histories()
            }

            R.id.nav_item_searchDish -> {
                searchDish()
            }

            R.id.nav_item_Main ->{
                val intent = Intent(this, DishAllActivity::class.java)
                startActivity(intent)
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun histories() {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }

    private fun searchDish() {
        val intent = Intent(this, DishListActivity::class.java)
        startActivity(intent)
    }

    */



}