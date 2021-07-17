package com.example.favdish.view.fragment.allDishes

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favdish.R
import com.example.favdish.databinding.FragmentAllDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.view.activites.AddUpdateDishActivity
import com.example.favdish.view.activites.MainActivity
import com.example.favdish.view.adapter.FavDishAdapter
import com.example.favdish.viewmodel.AllDishesViewModel


class AllDishesFragment : Fragment() {


    private lateinit var adapter: FavDishAdapter

    private var _binding: FragmentAllDishesBinding? = null
    private val binding get() = _binding!!


    private lateinit var allDishViewModel: AllDishesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allDishViewModel = ViewModelProvider(this).get(AllDishesViewModel::class.java)

        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
        adapter = FavDishAdapter { position ->
            val currentFavDish = adapter.currentList[position]
            dishDetails(currentFavDish)
        }
        binding.recyclerview.adapter = adapter

        allDishViewModel.allDishList?.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if(it.isNotEmpty()){
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.tvNoDishesAddedYet.visibility = View.GONE
                    adapter.submitList(it)
                }else{
                    binding.recyclerview.visibility = View.GONE
                    binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }

            }
        }
    }


    private fun dishDetails(favDish: FavDish) {
        findNavController().navigate(
            AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(
                favDish
            )
        )
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNavigation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
