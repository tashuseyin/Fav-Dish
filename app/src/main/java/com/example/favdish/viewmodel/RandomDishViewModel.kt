package com.example.favdish.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish
import com.example.favdish.model.entities.RandomDish
import com.example.favdish.model.network.RandomDishApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class RandomDishViewModel : ViewModel() {

    private val repository = FavDishRepository

    private val randomRecipeApiService = RandomDishApiService()
    private val compositeDisposable = CompositeDisposable()

    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDish>()
    val randomDishLoadingError = MutableLiveData<Boolean>()

    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)


    fun getRandomRecipeFromAPI() {
        loadRandomDish.value = true
        isRefreshing.value = false
        compositeDisposable.add(
            randomRecipeApiService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomDish>() {
                    override fun onSuccess(randomDish: RandomDish?) {
                        loadRandomDish.value = false
                        randomDishResponse.value = randomDish!!
                        randomDishLoadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loadRandomDish.value = false
                        randomDishLoadingError.value = true
                        e!!.printStackTrace()
                    }
                })
        )

    }


    fun refreshData() {
        isRefreshing.value = true
        getRandomRecipeFromAPI()
    }




    suspend fun insert(favDish: FavDish) {
        repository.insertFavDishData(favDish)
    }


}

