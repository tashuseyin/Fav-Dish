package com.example.favdish.utils

object Constants {

    const val  DISH_TYPE: String = "DishType"
    const val  DISH_CATEGORY: String = "DishCategory"
    const val  DISH_COOKING_TIME: String = "DishCookingTime"

    fun dishTypes(): ArrayList<String>{
        val list = ArrayList<String>()
        list.apply {
            add("Breakfast")
            add("Launch")
            add("Snacks")
            add("Dinner")
            add("Salad")
            add("Side Dish")
            add("Dessert")
            add("Other")
        }
        return list
    }

    fun dishCategory(): ArrayList<String>{
        val list = ArrayList<String>()
        list.apply {
            add("Pizza")
            add("BBQ")
            add("Bakery")
            add("Burger")
            add("Cafe")
            add("Chicken")
            add("Dessert")
            add("Drinks")
            add("Hot Dogs")
            add("Juices")
            add("Sandwich")
            add("Tea & Coffee")
            add("Wraps")
            add("Other")
        }
        return list
    }

    fun dishCookingTime(): ArrayList<String>{
        val list = ArrayList<String>()
        list.apply {
            add("10")
            add("15")
            add("20")
            add("30")
            add("45")
            add("50")
            add("60")
            add("90")
            add("120")
            add("150")
            add("180")
            add("Other")
        }
        return list
    }

}