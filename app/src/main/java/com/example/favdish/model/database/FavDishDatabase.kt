package com.example.favdish.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favdish.model.entities.FavDish

@Database(entities = [FavDish::class], version = 1)
abstract class FavDishDatabase : RoomDatabase() {

    abstract fun favDishDao(): FavDishDao

    companion object {
        @Volatile
        private var INSTANCE: FavDishDatabase? = null

        fun initializeDatabase(context: Context) {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    FavDishDatabase::class.java,
                    "favDish_database"
                ).build()
            }
        }

        fun getDatabase() = INSTANCE
    }
}

