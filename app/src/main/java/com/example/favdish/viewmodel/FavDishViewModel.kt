package com.example.favdish.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish
import kotlinx.coroutines.launch


class FavDishViewModel(private val repository:FavDishRepository) : ViewModel() {


    // viewModelScope.launch yerine suspend fun olarak tanımlasak yeterli aynı işi yapıyor
    fun insert(favDish: FavDish) = viewModelScope.launch {
        repository.insertFavDishData(favDish)
    }

    val allDishList: LiveData<List<FavDish>> = repository.getAllFavDish().asLiveData()

}



