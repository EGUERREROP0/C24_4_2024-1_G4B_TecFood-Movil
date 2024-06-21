package com.guerrero.erminio.tecfood.domain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.databinding.ActivityLogin2Binding
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper.get
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper.set
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login2Activity : AppCompatActivity() {

    private val apiService: ApiService by lazy { ApiService.create() }

    private lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initLogin()
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.btnSignGoogle.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logeo con google")
                .setMessage("Aun no disponible, estamos tranajando para tener esta funcionalidad")
                .setPositiveButton("Aceptar", null)
                .show()
        }
    }

    private fun initLogin() {
        val preferences = PreferenceHelper.defaultPrefs(this)

        if (preferences["token", ""].contains("."))
            goToMenu()
    }

    private fun createSessionPreference(token: String) {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["token"] = token

    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if( email.trim().isEmpty() || password.trim().isEmpty() ){
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val loginRequest = LoginRequest(email, password)

        val call = apiService.postLogin(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        Toast.makeText(
                            this@Login2Activity,
                            "Se produjo un error",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    if (loginResponse.message == "Login successful") {
                        createSessionPreference(loginResponse.token)
                        goToMenu()
                    } else {
                        Toast.makeText(
                            this@Login2Activity,
                            "Credenciales incorrectas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Token consola
                    Log.d("TokenActivity", "Token: ${loginResponse.token}")


                } else {
                    println("Response Code: ${response.code()}")
                    println("Response Message: ${response.message()}")
                    println("Response Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(
                        this@Login2Activity,
                        "Se produjo un error en el servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@Login2Activity, "Se produjo un error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun goToMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
