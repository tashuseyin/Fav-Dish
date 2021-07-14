package com.example.favdish.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favdish.databinding.ItemCustomListLayoutBinding
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish

class FavDishViewHolder(private val binding: ItemDishLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(favDish: FavDish, onItemClickListener: (Int) -> Unit){
            Glide.with(binding.ivDishImage.context).load(favDish.image).into(binding.ivDishImage)
            binding.tvDishTitle.text = favDish.title

            binding.root.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }
}