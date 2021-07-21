package com.example.favdish.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.favdish.databinding.ItemCustomListLayoutBinding


class CustomListItemViewHolder(private val binding: ItemCustomListLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item : String){
        binding.tvText.text = item
    }

}