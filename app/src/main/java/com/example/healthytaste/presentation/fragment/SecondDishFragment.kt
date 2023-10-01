package com.example.healthytaste.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthytaste.R
import com.example.healthytaste.databinding.FragmentDessertBinding
import com.example.healthytaste.databinding.FragmentSecondDishBinding


class SecondDishFragment : Fragment() {

    private lateinit var binding: FragmentSecondDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondDishBinding.inflate(inflater,container, false)
        return binding.root
    }


}