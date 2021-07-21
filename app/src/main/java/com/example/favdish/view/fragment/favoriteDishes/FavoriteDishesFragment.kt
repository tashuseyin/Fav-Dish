package com.example.favdish.view.fragment.favoriteDishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favdish.databinding.FragmentFavoriteDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.view.activites.MainActivity
import com.example.favdish.view.adapter.FavDishAdapter
import com.example.favdish.viewmodel.FavoriteViewModel

class FavoriteDishesFragment : Fragment() {

    private var _binding: FragmentFavoriteDishesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavDishAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
        adapter = FavDishAdapter(this) { position ->
            val currentFavDish = adapter.currentList[position]
            dishDetails(currentFavDish)
        }
        binding.recyclerview.adapter = adapter

        favoriteViewModel.favorites?.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if (it.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.tvNoFavoriteDishesAvailable.visibility = View.GONE
                    adapter.submitList(it)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.tvNoFavoriteDishesAvailable.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun dishDetails(favDish: FavDish) {
        findNavController().navigate(
            FavoriteDishesFragmentDirections.actionNavigationFavoriteDishesToDishDetailsFragment(favDish)
        )
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNavigation()
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).showBottomNavigation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}