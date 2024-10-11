package com.inf5d6.tp1
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.inf5d6.tp1.dataclasses.DetailsPokemon
import org.json.JSONObject

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

    fun getFavoris(listePokemonFav: MutableLiveData<Array<com.inf5d6.tp1.dataclasses.Pokemon>>) {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.GET,
            "https://pokemonsapi.herokuapp.com/favorites/",
            {
                val la = Gson().fromJson(it, Array<com.inf5d6.tp1.dataclasses.Pokemon>::class.java)
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

    fun authenticate(
        email: String,
        password: String,
        loginSuccess: MutableLiveData<Boolean>,
        errorMessage: MutableLiveData<String>
    ) {
        val url = "https://pokemonsapi.herokuapp.com/auth/token"
        val queue: RequestQueue = Volley.newRequestQueue(app)

        val body = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            body,
            { response ->
                val token = response.getString("token")
                MainActivity.TOKEN = token
                loginSuccess.postValue(true)
            },
            { error ->
                Log.d("Error.Response", error.message.toString())
                errorMessage.postValue("Authentication échouée. Réessayez.")
            }
        )
        queue.add(postRequest)
    }


}
