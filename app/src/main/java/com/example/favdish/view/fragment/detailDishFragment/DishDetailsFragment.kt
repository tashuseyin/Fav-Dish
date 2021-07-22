package com.example.favdish.view.fragment.detailDishFragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.viewmodel.DishDetailsViewModel
import kotlinx.coroutines.launch

class DishDetailsFragment : Fragment() {

    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var favDish: FavDish

    private lateinit var dishDetailsViewModel: DishDetailsViewModel
    private val args: DishDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        favDish = args.favDish

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share_dish -> {
                onShare()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShare() {
        val type = "text/plain"
        val subject = "Checkout this dish recipe"
        var extraText = ""
        val shareWith = "Share with"

        favDish.let {
            var image = ""

            if (it.imageSource == Constants.DISH_IMAGE_SOURCE_ONLINE) {
                image = it.image
            }else if(it.imageSource == Constants.DISH_IMAGE_SOURCE_LOCAL){
                image = it.image
            }

            var cookingInstructions = ""

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 cookingInstructions = Html.fromHtml(
                    it.directionCook,
                    Html.FROM_HTML_MODE_COMPACT
                ).toString()
            } else {
                @Suppress("DEPRECATION")
                cookingInstructions = Html.fromHtml(it.directionCook).toString()
            }

            extraText =
                "$image \n" +
                        "\n Title:  ${it.title} \n\n Type: ${it.type} \n\n Category: ${it.category}" +
                        "\n\n Ingredients: \n ${it.ingredients} \n\n Instructions To Cook: \n $cookingInstructions" +
                        "\n\n Time required to cook the dish approx ${it.cookingTime} minutes."
        }

        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = type
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sendIntent.putExtra(Intent.EXTRA_TEXT, extraText)
        startActivity(Intent.createChooser(sendIntent, shareWith))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}