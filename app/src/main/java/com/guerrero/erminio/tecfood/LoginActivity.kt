package com.guerrero.erminio.tecfood

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
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
import androidx.core.widget.doOnTextChanged
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

        //Add the callback to the activity
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        //Disguise LinearLayout for Terms and Conditions
        lyTerms = findViewById(R.id.lyTerms)
        lyTerms.visibility = View.INVISIBLE

        //Getting values of the vars
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()

        //Validar email
        manageButtonLogin()
        etEmail.doOnTextChanged() { text, start, before, count ->
            manageButtonLogin()
        }
        etPassword.doOnTextChanged() { text, start, before, count ->
            manageButtonLogin()
        }

    }

    //If the user is already logged, go to the home.
    public override fun onStart(){
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if(currentUser != null) goHome(currentUser.email.toString(), currentUser.providerId)

    }

    //If the button is pressed, the application will close
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val startMain=Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        }
    }

    //Validar el email
    private fun manageButtonLogin(){
        //assigning values to the vars email and password
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        var btnLogin = findViewById<View>(R.id.btnLogin)

        btnLogin.isEnabled = !(TextUtils.isEmpty(password) || !ValidateEmail.isEmail(email))

        //Second form
        /*
        if(TextUtils.isEmpty(password) || !ValidateEmail.isEmail(email)) {
            btnLogin.setBackgroundColor(resources.getColor(R.color.gray, null))
            //btnLogin.setBackgroundResource(R.drawable.raund_button)
            btnLogin.isEnabled = false
        }
        else btnLogin.isEnabled = true
        */
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

    // Function to go to terms of the user
    fun goTerms(v: View) {
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
    // Function to go to forgot password
    fun forgotPassword(v: View) {
       // val intent = Intent(this, ForgotPasswordActivity::class.java)
       // startActivity(intent)
        resetPassword()
    }

    //Function to recover a password
    private fun resetPassword(){
        var email = etEmail.text.toString()
        if(!TextUtils.isEmpty(email)){
            mAuth.sendPasswordResetEmail(email) //Var Auth
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) Toast.makeText(this, "Email enviado a $email", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this, "No se a encontrado un usuario con ese correo", Toast.LENGTH_SHORT).show()
                }
           }
        else Toast.makeText(this, "Por favor ingrese un correo", Toast.LENGTH_SHORT).show()
    }
}