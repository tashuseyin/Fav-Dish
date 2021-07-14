package com.example.favdish.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish

class AllDishesViewModel : ViewModel() {

    private val repository = FavDishRepository

    private var _allDishList: LiveData<List<FavDish>>? = allFavDish()
    val allDishList =_allDishList


    private fun allFavDish()  = repository.getAllFavDish()


}