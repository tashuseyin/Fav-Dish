package com.example.favdish.view.fragment.detailDishFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favdish.R
import com.example.favdish.databinding.FragmentDishDetailsBinding
import com.example.favdish.databinding.FragmentFavoriteDishesBinding

class DishDetailsFragment : Fragment() {

    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}