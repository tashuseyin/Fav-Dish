package com.example.favdish.model.database


import androidx.lifecycle.LiveData
import com.example.favdish.model.entities.FavDish

object FavDishRepository {

    private val favDishDao by lazy {
        FavDishDatabase.getDatabase()?.favDishDao()
    }

    suspend fun insertFavDishData(favDish: FavDish) {
        favDishDao?.insertFavDishDetail(favDish)
    }

    fun getAllFavDish() = favDishDao?.getAllFavDishes()

    suspend fun updateFavDish(favDish: FavDish) = favDishDao?.updateFavDish(favDish)

    fun getFavoriteDishes() = favDishDao?.getFavoriteDishesList()

    suspend fun deleteFavDishes(favDish: FavDish){
        favDishDao?.deleteFavDish(favDish)
    }

    fun filteredListDishes(value : String) = favDishDao?.getFilteredDishesList(value)


}