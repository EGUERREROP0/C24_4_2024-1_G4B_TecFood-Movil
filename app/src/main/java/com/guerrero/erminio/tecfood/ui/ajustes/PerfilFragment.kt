package com.guerrero.erminio.tecfood.ui.ajustes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guerrero.erminio.tecfood.ui.login.LoginActivity
import com.guerrero.erminio.tecfood.databinding.FragmentPerfilBinding
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper
import com.guerrero.erminio.tecfood.data.network.PreferenceHelper.get


class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        signOut()
        loadUserProfile()
        return binding.root
    }


    private fun signOut() {
        binding.lylySignOutAll.setOnClickListener {
            performLogout()
        }
    }


    private fun performLogout(){
        clearSesion()
        goToLogin()
    }

    private fun clearSesion(){
        val preferences = PreferenceHelper.defaultPrefs(requireContext())
        preferences.edit().clear().apply()
    }

    private fun goToLogin(){
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun loadUserProfile() {
        val preferences = PreferenceHelper.defaultPrefs(requireContext())
        val userEmail = preferences["userEmail", ""]
        val userName = preferences["userName", ""]

        binding.tvUserName.text = userName.uppercase()
        binding.tvUserEmail.text = userEmail

    }


    }