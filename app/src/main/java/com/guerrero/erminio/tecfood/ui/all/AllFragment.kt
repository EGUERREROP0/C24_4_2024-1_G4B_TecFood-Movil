package com.guerrero.erminio.tecfood.ui.all

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerrero.erminio.tecfood.data.network.ApiService
import com.guerrero.erminio.tecfood.data.network.RetrofitInstance
import com.guerrero.erminio.tecfood.databinding.FragmentAllBinding
import com.guerrero.erminio.tecfood.ui.all.DetailDishActivity.Companion.DISH_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class AllFragment : Fragment() {

    private lateinit var drawer: DrawerLayout
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!

    private lateinit var retrofit: Retrofit
    private lateinit var adapter: DishAllAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)

        // Initialize Retrofit and adapter here
        retrofit = RetrofitInstance.getRetrofit
        adapter = DishAllAdapter { navegateToDetailActivity(it) }

        // Set the RecyclerView's adapter here
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = adapter

        initUI()

        return binding.root

    }

    private fun initUI() {
        getAllDishes()
    }

    private fun getAllDishes() {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = retrofit.create(ApiService::class.java).getPokemon(100)
                if (request.isSuccessful) {
                    request.body()?.let {
                        requireActivity().runOnUiThread {
                            adapter.updateAllList(it.categories)
                            binding.progressBar.isVisible = false
                        }
                    }
                } else {
                    Log.i("response", "Error..")
                }
            } catch (e: IOException) {
                requireActivity().runOnUiThread {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setTitle("Confirmacion")
                        .setMessage("No se pudo conectar al servidor. Por favor, verifica tu conexión a internet e inténtalo de nuevo.")
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                    binding.progressBar.isVisible = true
                }
            } catch (e: HttpException) {
                requireActivity().runOnUiThread {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvError.visibility = View.VISIBLE
                    binding.progressBar.isVisible = false

                }
            }
        }
    }

    //Click en producto llevar a detalle
    private fun navegateToDetailActivity(id: Int) {
        var intent = Intent(requireContext(), DetailDishActivity::class.java)
        intent.putExtra(DISH_ID, id)
        startActivity(intent)
    }


}


