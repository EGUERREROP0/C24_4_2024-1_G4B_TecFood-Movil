package com.guerrero.erminio.tecfood.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guerrero.erminio.tecfood.R
import com.guerrero.erminio.tecfood.databinding.FragmentAllBinding
import com.guerrero.erminio.tecfood.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private  var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
       return binding.root
    }


}