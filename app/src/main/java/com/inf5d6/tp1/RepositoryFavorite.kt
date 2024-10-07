package com.inf5d6.tp1.ui.favorites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.inf5d6.tp1.MainActivity
import com.inf5d6.tp1.dataclasses.Pokemon

class RepositoryFavorite(val app: Application) {
    fun getFavoris(listePokemonFav: MutableLiveData<Array<Pokemon>>) {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.GET,
            "https://pokemonsapi.herokuapp.com/favorites/",
            {
                val la = Gson().fromJson(it, Array<Pokemon>::class.java)
                listePokemonFav.postValue(la)
            },
            {
                println(it.message)
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${MainActivity.TOKEN}"
                return headers
            }
        }
        q.add(r)
    }
}