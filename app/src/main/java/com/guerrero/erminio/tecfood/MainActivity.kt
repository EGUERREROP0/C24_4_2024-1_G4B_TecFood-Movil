package com.guerrero.erminio.tecfood

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.guerrero.erminio.tecfood.LoginActivity.Companion.useremail

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toast.makeText(this, "Welcome to TecFood $useremail", Toast.LENGTH_SHORT).show()

        initToolbar()
        initNavigationView()

    }
    // Function to initialize the toolbar
    private fun initToolbar(){
        //enableEdgeToEdge()
            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
            setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.bar_title,
                R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    //Function to inicialize the navigation view
    private fun initNavigationView() {
        var navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var headerView: View = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        var tvUser = headerView.findViewById<TextView>(R.id.tvUser)
        tvUser.text = useremail

    }


    fun callSignOut(){
        signOut()
    }
    private fun signOut(){
        useremail = ""
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    // dar vida al menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_record -> histories()
            R.id.nav_item_signOut -> callSignOut()
        }
        //Volver el menu a su estado original
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun histories(){
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

}