package com.example.favdish.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish

class FavoriteViewModel : ViewModel() {

    private val repository = FavDishRepository

    private var _favorites: LiveData<List<FavDish>>? = getListFavoriteDishes()
    val favorites = _favorites


    private fun getListFavoriteDishes() = repository.getFavoriteDishes()
}