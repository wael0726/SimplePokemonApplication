package com.inf5d6.tp1.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.inf5d6.tp1.Pokemon
import com.inf5d6.tp1.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    val listePokemons = MutableLiveData<Array<Pokemon>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(getApplication()).getPokemons(listePokemons)
        }
    }
}