package com.example.favdish.model.database


import com.example.favdish.model.entities.FavDish

object FavDishRepository {

    private val favDishDao by lazy {
        FavDishDatabase.getDatabase()?.favDishDao()
    }

    suspend fun insertFavDishData(favDish: FavDish) {
        favDishDao?.insertFavDishDetail(favDish)
    }

    fun getAllFavDish() = favDishDao?.getAllFavDishes()


}