package com.guerrero.erminio.tecfood.ui.pay

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mesajeBottonContinueToPay()
    }

    private fun mesajeBottonContinueToPay(){
        binding.tvContinuePayment.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("¡Pago exitoso!")
                .setMessage("¡Gracias por tu compra!")
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}