package com.example.favdish.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.favdish.model.entities.FavDish

@Dao
interface FavDishDao {

    @Insert
    suspend fun insertFavDishDetail(favDish : FavDish)


}