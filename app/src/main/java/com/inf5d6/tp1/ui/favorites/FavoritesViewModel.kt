package com.inf5d6.tp1.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.inf5d6.tp1.Repository
import com.inf5d6.tp1.dataclasses.Pokemon

class FavoritesViewModel(val app: Application) : AndroidViewModel(app) {
    val listePokemonFav = MutableLiveData<Array<Pokemon>>()
    init {
        loadFavorites()
    }

    fun loadFavorites() {
        Repository(app).getFavoris(listePokemonFav)
    }
}