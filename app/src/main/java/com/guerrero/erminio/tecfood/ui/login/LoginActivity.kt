package com.guerrero.erminio.tecfood.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper.get
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper.set
import com.guerrero.erminio.tecfood.databinding.ActivityLoginBinding
import com.guerrero.erminio.tecfood.ui.home.MainActivity
import com.guerrero.erminio.tecfood.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy { ApiService.create() }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLogin()
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.btnSignGoogle.setOnClickListener {
            showAlertDialogWithToastMessage("Aun no disponible, estamos trabajando para tener esta funcionalidad")
        }

        binding.tvGoRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun initLogin() {
        val preferences = PreferenceHelper.defaultPrefs(this)

        if (preferences["token", ""].contains("."))
            goToMenu()
    }

    private fun createSessionPreference(token: String, user: Data) {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["token"] = token
        preferences["userEmail"] = user.email
        preferences["userName"] = "${user.lastName}, ${user.firstName}"
        Log.d("LoginActivity", "Token: $token")
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            showAlertDialogWithToastMessage("Campos vacios. Por favor, llene todos los campos")
            return
        }

        val loginRequest = LoginRequest(email, password)

        val call = apiService.postLogin(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        showAlertDialogWithToastMessage("Se produjo un error")
                        return
                    }
                    if (response.code() == 200) {
                        createSessionPreference(loginResponse.token, loginResponse.user)
                        goToMenu()

                    } else {
                        showAlertDialogWithToastMessage("Credenciales incorrectas")
                    }
                    // Token consola
                    Log.d("TokenActivity", "Token: ${loginResponse.token}")


                }  else {
                    if (response.code() == 401) {
                        showAlertDialogWithToastMessage("Credenciales incorrectas")
                    } else {
                        println("Response Code: ${response.code()}")
                        println("Response Message: ${response.message()}")
                        println("Response Error Body: ${response.errorBody()?.string()}")
                        showAlertDialogWithToastMessage("Credenciales incorrectas")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Se produjo un error en el servidor",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
    }

    private fun goToMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Mesaje
    fun showAlertDialogWithToastMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Mensaje")
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}