package com.example.favdish.view.fragment.randomDish

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.favdish.R
import com.example.favdish.databinding.FragmentRandomDishBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.model.entities.Recipe
import com.example.favdish.utils.Constants
import com.example.favdish.viewmodel.RandomDishViewModel
import kotlinx.coroutines.launch

class RandomDishFragment : Fragment() {


    private var _binding: FragmentRandomDishBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressDialog: Dialog
    private lateinit var randomDishViewModel: RandomDishViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)

        randomDishViewModel.getRandomRecipeFromAPI()

        randomDishViewModelObserver()

        binding.swipeRefresh.setOnRefreshListener {
            randomDishViewModel.refreshData()
        }
    }


    private fun randomDishViewModelObserver() {
        randomDishViewModel.randomDishResponse.observe(viewLifecycleOwner) { randomDish ->
            setRandomDishResponseInUI(randomDish.recipes[0])
            randomDishViewModel.isRefreshing.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = it
            }
        }

        randomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner) { dataError ->
            Log.e("Random Dish API Error", "$dataError")
        }

        randomDishViewModel.loadRandomDish.observe(viewLifecycleOwner) { loadRandomDish ->
            if(loadRandomDish){
                showCustomProgressDialog()
            }else{
                hideCustomProgressbar()
            }


        }
    }

    @SuppressLint("DEPRACATION", "SetTextI18n")
    private fun setRandomDishResponseInUI(recipe: Recipe) {
        Glide.with(this)
            .load(recipe.image)
            .centerCrop()
            .into(binding.randomDishImage)

        binding.randomDishTitle.text = recipe.title
        var dishType = "Other"
        if (recipe.dishTypes.isNotEmpty()) {
            dishType = recipe.dishTypes[0]
            binding.randomDishType.text = dishType
        }
        binding.randomDishCategory.text = "Other"

        var ingredients = ""
        for (value in recipe.extendedIngredients) {
            ingredients = if (ingredients.isEmpty()) {
                value.original
            } else {
                ingredients + ", \n" + value.original
            }
        }
        binding.randomDishIngredientsValue.text = ingredients

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.randomDishDirectionCooking.text =
                Html.fromHtml(recipe.instructions, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            binding.randomDishDirectionCooking.text = Html.fromHtml(recipe.instructions)
        }

        binding.randomDishCookingTime.text =
            getString(R.string.lbl_estimate_cooking_time, recipe.readyInMinutes.toString())

        binding.ivFavoriteDish.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        var addedToFavorite = false

        binding.ivFavoriteDish.setOnClickListener {

            if (addedToFavorite) {
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.msg_already_added_to_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val randomDishDetails = FavDish(
                    recipe.image,
                    Constants.DISH_IMAGE_SOURCE_ONLINE,
                    recipe.title,
                    dishType,
                    "Other",
                    ingredients,
                    recipe.readyInMinutes.toString(),
                    recipe.instructions,
                    true
                )

                lifecycleScope.launch {
                    randomDishViewModel.insert(randomDishDetails)
                }

                addedToFavorite = true

                binding.ivFavoriteDish.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.Favorite_added),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun showCustomProgressDialog() {
        progressDialog = Dialog(requireContext())

        progressDialog.setContentView(R.layout.dialog_custom_progressbar)
        progressDialog.show()

    }

    private fun hideCustomProgressbar() {
        progressDialog.dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}