package com.guerrero.erminio.tecfood.ui.ajustes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.guerrero.erminio.tecfood.LoginActivity
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        signOutF()

        return binding.root
    }

   private fun signOutF(){
       binding.cerrar.setOnClickListener{
           signOut()
       }
   }

    private fun signOut() {
        LoginActivity.useremail = ""
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)

    }

}