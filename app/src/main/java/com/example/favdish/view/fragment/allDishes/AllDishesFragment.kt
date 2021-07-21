package com.example.favdish.view.fragment.allDishes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favdish.R
import com.example.favdish.databinding.DialogCustomListBinding
import com.example.favdish.databinding.FragmentAllDishesBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.view.activites.AddUpdateDishActivity
import com.example.favdish.view.activites.MainActivity
import com.example.favdish.view.adapter.CustomListItemAdapter
import com.example.favdish.view.adapter.FavDishAdapter
import com.example.favdish.viewmodel.AllDishesViewModel
import kotlinx.coroutines.launch


class AllDishesFragment : Fragment() {


    private lateinit var adapter: FavDishAdapter
    private lateinit var customDialog: Dialog

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

        adapter = FavDishAdapter(this) { position ->
            val currentFavDish = adapter.currentList[position]
            dishDetails(currentFavDish)
        }
        binding.recyclerview.adapter = adapter

        observeAllDishList()
    }

    private fun observeAllDishList() {
        allDishViewModel.allDishList?.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if (it.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.tvNoDishesAddedYet.visibility = View.GONE
                    adapter.submitList(it)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }
            }
        }
    }

    fun dishDelete(favDish: FavDish) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.title_delete_dish))
        builder.setMessage(getString(R.string.msg_delete_title_dishes, favDish.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(getString(R.string.lbl_yes)) { dialogInterface, _ ->
            lifecycleScope.launch {
                allDishViewModel.deleteFavDish(favDish)
                dialogInterface.dismiss()
            }
        }
        builder.setNegativeButton(getString(R.string.lbl_no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun dishDetails(favDish: FavDish) {
        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(favDish))
        if (requireActivity() is MainActivity) {
            (activity as MainActivity).hideBottomNavigation()
        }
    }


    private fun filterDishesListDialog() {
        customDialog = Dialog(requireContext())
        val view = DialogCustomListBinding.inflate(layoutInflater)
        customDialog.setContentView(view.root)

        view.tvTitle.text = getString(R.string.title_select_item_to_filter)
        val dishTypes = Constants.dishTypes()
        dishTypes.add(0, Constants.ALL_ITEMS)

        val adapter =
            CustomListItemAdapter(requireContext(), this, dishTypes, Constants.FILTER_SELECTION)
        view.recyclerview.adapter = adapter
        customDialog.show()
    }


    fun filterSelection(filterItemSelection: String) {
        customDialog.dismiss()

        Log.i("TAG", filterItemSelection)

        if (filterItemSelection == Constants.ALL_ITEMS) {
            observeAllDishList()
        } else {

            allDishViewModel.getFilteredListDishes(filterItemSelection)
                ?.observe(viewLifecycleOwner) { dishes ->
                    dishes.let {
                        if (it.isNotEmpty()) {
                            binding.recyclerview.visibility = View.VISIBLE
                            binding.tvNoDishesAddedYet.visibility = View.GONE
                            adapter.submitList(it)
                        } else {
                            binding.recyclerview.visibility = View.GONE
                            binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                        }
                    }
                }
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
            R.id.item_filter -> {
                filterDishesListDialog()
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
