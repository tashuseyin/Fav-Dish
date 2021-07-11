package com.example.favdish.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favdish.databinding.ItemCustomListLayoutBinding

class CustomListItemAdapter(private val listItems: List<String>, private val selection: String) :
    RecyclerView.Adapter<CustomListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListItemViewHolder {
        val binding =
            ItemCustomListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomListItemViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount() = listItems.size
}