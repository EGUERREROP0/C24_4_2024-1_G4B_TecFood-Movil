package com.guerrero.erminio.tecfood.ui.ajustes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.guerrero.erminio.tecfood.LoginActivity
import com.guerrero.erminio.tecfood.LoginActivity.Companion.useremail
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.FragmentPerfilBinding
import com.guerrero.erminio.tecfood.ui.ajustes.dataStore.DataStaoreClass
import com.guerrero.erminio.tecfood.ui.ajustes.dataStore.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        signOutF()
        initNavigationView()

        //datos dataClass()
        /*CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firsTime }.collect{ settingModel ->
                if( settingModel!= null){
                    requireActivity().runOnUiThread{
                        //binding.volumen.setValues( settingModel.volume.toFloat())
                        binding.swDarkMode.isChecked =  settingModel.darkMode
                        firsTime = !firsTime
                    }
                }
            }
        }

        initUI()*/

        return binding.root
    }


   /* private fun initUI() {
        binding.swDarkMode.setOnCheckedChangeListener { _, value ->
            if (value) {
                  enableDarkMode()
            } else {
                  desabledarkmode()
            }

            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(DARK_MODE_KEY, value)
            }
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        requireContext().dataStore.edit { preferende ->
            preferende[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<DataStaoreClass?> {
        return requireContext().dataStore.data.map { preferences ->
            DataStaoreClass(
                volume = preferences[intPreferencesKey(VOLUME_KEY)] ?: 50,
                darkMode = preferences[booleanPreferencesKey(DARK_MODE_KEY)] ?: true
            )
        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun desabledarkmode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    */

        private fun signOutF() {
            binding.lylySignOutAll.setOnClickListener {
                signOut()
            }
        }

        private fun signOut() {
            LoginActivity.useremail = ""
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

        }

        private fun initNavigationView() {

            val tvUser = binding.tvUser
            tvUser.text = useremail

        }

        //Funcianalidad para modo oscuro


    }