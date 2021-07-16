package com.example.favdish.view.fragment.detailDishFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.favdish.R
import com.example.favdish.databinding.FragmentDishDetailsBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DishDetailsFragmentArgs by navArgs()
        Glide.with(this).load(args.favDish.image).into(binding.dishDetailImage)
        binding.dishDetailTitle.text = args.favDish.title
        binding.dishDetailType.text = args.favDish.type
        binding.dishDetailCategory.text = args.favDish.category
        binding.dishDetailIngredientsValue.text = args.favDish.ingredients
        binding.dishDetailDirectionCooking.text = args.favDish.directionCook
        binding.dishDetailCookingTime.text = resources.getString(R.string.lbl_estimate_cooking_time, args.favDish.cookingTime)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}