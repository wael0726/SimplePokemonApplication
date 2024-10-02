package com.inf5d6.tp1
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {
    val listePokemons = MutableLiveData<Array<Pokemon>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getPokemons(listePokemons)
        }
    }
}
