package com.example.favdish.view.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.PopupMenu
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favdish.R
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish
import com.example.favdish.utils.Constants
import com.example.favdish.view.activites.AddUpdateDishActivity


class FavDishViewHolder(private val binding: ItemDishLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(favDish: FavDish, onItemClickListener: (Int) -> Unit) {
        binding.tvDishTitle.text = favDish.title

        val imageButton = binding.menuButton

        imageButton.setOnClickListener { view ->
            val popup = PopupMenu(view.context, imageButton)
            popup.menuInflater.inflate(R.menu.menu_adapter, popup.menu)
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish) {
                    val intent =
                        Intent(view.context, AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, favDish)
                    view.context.startActivity(intent)

                } else if (it.itemId == R.id.action_delete_dish) {
                    Log.i("You have clicked on", "Delete Option of ${favDish.title}")
                }
                true
            }
            popup.show()
        }

        Glide.with(binding.ivDishImage.context).load(favDish.image)
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
                                binding.cardItem.setCardBackgroundColor(rgb)
                            }
                    }
                    return false
                }
            })
            .into(binding.ivDishImage)

        binding.root.setOnClickListener {
            onItemClickListener(adapterPosition)
        }
    }

}


