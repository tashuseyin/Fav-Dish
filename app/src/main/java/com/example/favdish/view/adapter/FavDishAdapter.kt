package com.example.favdish.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.favdish.databinding.ItemDishLayoutBinding
import com.example.favdish.model.entities.FavDish

class FavDishAdapter(private val onItemClickListener: (Int) -> Unit) : ListAdapter<FavDish, FavDishViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavDishViewHolder {
        val binding =
            ItemDishLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavDishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavDishViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<FavDish>() {
        override fun areItemsTheSame(oldItem: FavDish, newItem: FavDish): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: FavDish, newItem: FavDish): Boolean {
            return oldItem == newItem
        }
    }
}