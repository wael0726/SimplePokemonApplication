package com.inf5d6.tp1
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class Repository(val app: Application) {
    fun getPokemons(listePokemon : MutableLiveData<Array<Pokemon>>) {
        val q = Volley.newRequestQueue(app)
        val r = StringRequest(
            Request.Method.GET,
            "https://pokemonsapi.herokuapp.com/pokemons/",
            {
                val la = Gson().fromJson(it, Array<Pokemon>::class.java)
                listePokemon.postValue(la)
            },
            {}
        )
        q.add(r)
    }
}