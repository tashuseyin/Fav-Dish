package com.example.favdish.model.database

import androidx.annotation.WorkerThread
import com.example.favdish.model.entities.FavDish

object FavDishRepository{

    private val favDishDao by lazy {
        FavDishDatabase.getDatabase()?.favDishDao()
    }

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao?.insertFavDishDetail(favDish)
    }
}