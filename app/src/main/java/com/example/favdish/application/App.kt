package com.example.favdish.application

import android.app.Application
import com.example.favdish.model.database.FavDishDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FavDishDatabase.initializeDatabase(applicationContext)
    }

}