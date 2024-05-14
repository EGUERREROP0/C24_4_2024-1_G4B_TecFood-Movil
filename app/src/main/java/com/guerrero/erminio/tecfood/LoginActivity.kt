package com.guerrero.erminio.tecfood

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.ActionProvider
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {
    companion object {
        lateinit var useremail: String
        lateinit var providerSession: String
    }

    //variables
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var  etEmail: EditText
    private lateinit var  etPassword: EditText
    private lateinit var  lyTerms: LinearLayout

    //Variable para Auth whit DDBB
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Disguise LinearLayout for Terms and Conditions
        lyTerms = findViewById(R.id.lyTerms)
        lyTerms.visibility = View.INVISIBLE

        //Getting values of the vars
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()
    }

    //Function to login for public
    fun login(view: View) {
        loginUser()
    }

    private fun loginUser(){
        //assigning values to the vars email and password
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        //start session if is succesful
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) goHome(email, "email") //if is succesful go to home
                else {
                    if( lyTerms.visibility == View.INVISIBLE) lyTerms.visibility = View.VISIBLE
                    else{
                        var cbAcept = findViewById<CheckBox>(R.id.cbAcept)
                        if(cbAcept.isChecked)  register()
                    }

                }

            }
    }

    private fun goHome(email: String, provider: String){
        useremail = email
        providerSession = provider

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Write in the DDBB register
    private fun register(){
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task -> //Lambda
                if (task.isSuccessful) {
                    //Date register
                    var dateRegister = SimpleDateFormat("dd/MM/yyyy").format(Date())

                    var dbReister = FirebaseFirestore.getInstance()
                    dbReister.collection("users").document(email).set(hashMapOf(

                        "user" to email,
                        "dateRegister" to dateRegister
                    ))

                    goHome(email, "email") //Redirect to Login
                }
                else Toast.makeText(this, "Error algo salio mal :(", Toast.LENGTH_SHORT).show()
            }
    }
}