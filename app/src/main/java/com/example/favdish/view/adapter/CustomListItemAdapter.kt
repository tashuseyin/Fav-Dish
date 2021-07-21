package com.example.favdish.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.favdish.databinding.ItemCustomListLayoutBinding
import com.example.favdish.view.activites.AddUpdateDishActivity
import com.example.favdish.view.fragment.allDishes.AllDishesFragment

class CustomListItemAdapter(
    private val context: Context,
    private val fragment: Fragment?,
    private val listItems: List<String>,
    private val selection: String
) :
    RecyclerView.Adapter<CustomListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListItemViewHolder {
        val binding =
            ItemCustomListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomListItemViewHolder, position: Int) {
        val item = listItems[position]
        holder.bind(item)


        holder.itemView.setOnClickListener {
            if (context is AddUpdateDishActivity) {
                context.selectedListItem(item, selection)
            }

            if (fragment is AllDishesFragment){
                fragment.filterSelection(item)
            }
        }
    }

    override fun getItemCount() = listItems.size
}