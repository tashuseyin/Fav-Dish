package com.example.favdish.view.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish


class FavDishViewHolder(private val binding: ItemDishLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(favDish: FavDish, onItemClickListener: (Int) -> Unit) {
        binding.tvDishTitle.text = favDish.title
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
                        Palette.from(resource.toBitmap()).generate {
                            val rgb = it?.lightVibrantSwatch?.rgb ?: 0
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