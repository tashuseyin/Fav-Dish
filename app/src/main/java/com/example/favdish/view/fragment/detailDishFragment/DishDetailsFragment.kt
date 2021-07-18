package com.example.favdish.view.fragment.detailDishFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favdish.R
import com.example.favdish.databinding.FragmentDishDetailsBinding
import com.example.favdish.viewmodel.DishDetailsViewModel
import kotlinx.coroutines.launch

class DishDetailsFragment : Fragment() {

    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var dishDetailsViewModel: DishDetailsViewModel
    private val args: DishDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dishDetailsViewModel = ViewModelProvider(this).get(DishDetailsViewModel::class.java)
        getDishDetails()

    }

    private fun getDishDetails() {

        Glide.with(this).load(args.favDish.image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("TAG", "Error loading image", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        Palette.from(it.toBitmap())
                            .generate { palette ->
                                val rgb = palette?.mutedSwatch?.rgb ?: 0
                                binding.rlDishDetailMain.setBackgroundColor(rgb)
                            }
                    }
                    return false
                }
            })
            .into(binding.dishDetailImage)
        binding.dishDetailTitle.text = args.favDish.title
        binding.dishDetailType.text = args.favDish.type
        binding.dishDetailCategory.text = args.favDish.category
        binding.dishDetailIngredientsValue.text = args.favDish.ingredients
        binding.dishDetailDirectionCooking.text = args.favDish.directionCook
        binding.dishDetailCookingTime.text =
            resources.getString(R.string.lbl_estimate_cooking_time, args.favDish.cookingTime)

        isCheckFavorite()

        binding.ivFavoriteDish.setOnClickListener {
            args.favDish.isFavoriteDish = !args.favDish.isFavoriteDish

            lifecycleScope.launch {
                dishDetailsViewModel.updateFavDishData(args.favDish)
            }
            isCheckFavorite()
        }

    }

    private fun isCheckFavorite() {
        if (args.favDish.isFavoriteDish) {
            binding.ivFavoriteDish.setImageResource(R.drawable.ic_baseline_favorite_24)
            Toast.makeText(
                context,
                resources.getString(R.string.Favorite_added),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding.ivFavoriteDish.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            Toast.makeText(
                context,
                resources.getString(R.string.Favorite_removed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}