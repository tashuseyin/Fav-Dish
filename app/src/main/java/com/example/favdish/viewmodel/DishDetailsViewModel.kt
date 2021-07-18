package com.example.favdish.viewmodel

import androidx.lifecycle.ViewModel
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish

class DishDetailsViewModel : ViewModel() {
    private val repository = FavDishRepository

    suspend fun updateFavDishData(favDish: FavDish) {
        repository.updateFavDish(favDish)
    }


}