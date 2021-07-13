package com.example.favdish.application

import android.app.Application
import com.example.favdish.model.database.FavDishDatabase
import com.example.favdish.model.database.FavDishRepository

class App : Application() {

    val database by lazy { FavDishDatabase.getDatabase(applicationContext) }
    val repository by lazy { FavDishRepository(database.favDishDao()) }
}