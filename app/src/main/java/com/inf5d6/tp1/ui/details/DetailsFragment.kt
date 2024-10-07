package com.inf5d6.tp1.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.inf5d6.tp1.MainActivity
import com.inf5d6.tp1.R
import com.inf5d6.tp1.dataclasses.DetailsPokemon
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val detailsViewModelFactory = ViewModelProvider(this)[DetailsViewModel::class.java]

        val pokemonId = arguments?.getInt("pokemonId")
        if (pokemonId != null) {
            fetchPokemonDetails(pokemonId)
        } else {
            Toast.makeText(context, "No Pokemon ID", Toast.LENGTH_SHORT).show()
        }

        val btnFavorite = view.findViewById<Button>(R.id.FavorisButton)
        btnFavorite.setOnClickListener {
            if (isFavorite) {
                removeFromFavorites(pokemonId!!)
            } else {
                addToFavorites(pokemonId!!)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(pokemonDetails: DetailsPokemon) {
        view?.findViewById<TextView>(R.id.namepokemon2)?.text = pokemonDetails.name
        Picasso.get().load(pokemonDetails.imgURL).into(view?.findViewById(R.id.imgURL2))
        view?.findViewById<TextView>(R.id.habitat)?.text = "Habitat: ${pokemonDetails.habitat.name}"
        view?.findViewById<TextView>(R.id.species)?.text = "Species: ${pokemonDetails.species.name}"
        view?.findViewById<TextView>(R.id.poketype)?.text =  "Poketypes: ${pokemonDetails.poketypes.joinToString(", ") { it.name }}"

        val evolutionsList = mutableListOf<DetailsPokemon>()
        var currentEvolution: DetailsPokemon? = pokemonDetails.evolution
        while (currentEvolution != null) {
            evolutionsList.add(currentEvolution)
            currentEvolution = currentEvolution.evolution
        }

    }

    private fun fetchPokemonDetails(pokemonId: Int) {
        val url = "https://pokemonsapi.herokuapp.com/pokemon?pokemonId=$pokemonId"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                val pokemonDetails = Gson().fromJson(it, DetailsPokemon::class.java)
                updateUI(pokemonDetails)
                checkFavorite(pokemonDetails.pokemonId)
            },
            {
                println(it.message)
            }
        )

        queue.add(stringRequest)
    }

    private fun checkFavorite(pokemonId: Int) {
        val url = "https://pokemonsapi.herokuapp.com/favorite?pokemonId=$pokemonId"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = object : StringRequest(
            Method.GET,
            url,
            {
                val jsonResponse = Gson().fromJson(it, Map::class.java)
                if (jsonResponse.containsKey("isFavorite")) {
                    isFavorite = jsonResponse["isFavorite"] as Boolean
                    updateFavoriteButton()
                } else {
                    Toast.makeText(requireContext(), "Erreur verification favoris", Toast.LENGTH_SHORT).show()
                }
            },
            {
                println("Erreur verification favoris: ${it.message}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${MainActivity.TOKEN}"
                return headers
            }
        }
        queue.add(stringRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun updateFavoriteButton() {
        val btnFavorite = view?.findViewById<Button>(R.id.FavorisButton)
        if (isFavorite) {
            btnFavorite?.text = "Supprimer des favoris"
        } else {
            btnFavorite?.text = "Ajouter aux favoris"
        }
    }

    private fun addToFavorites(pokemonId: Int) {
        val url = "https://pokemonsapi.herokuapp.com/favorite?pokemonId=$pokemonId"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            {
                Toast.makeText(requireContext(), "Ajoute aux favoris", Toast.LENGTH_SHORT).show()
                isFavorite = true
                updateFavoriteButton()
            },
            {
                Toast.makeText(requireContext(), "Erreur ajout", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${MainActivity.TOKEN}"
                return headers
            }
        }
        queue.add(stringRequest)
    }

    private fun removeFromFavorites(pokemonId: Int) {
        val url = "https://pokemonsapi.herokuapp.com/favorite?pokemonId=$pokemonId"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = object : StringRequest(
            Method.DELETE,
            url,
            {
                Toast.makeText(requireContext(), "Supprime des favoris", Toast.LENGTH_SHORT).show()
                isFavorite = false
                updateFavoriteButton()
            },
            {
                Toast.makeText(requireContext(), "Erreur suppression", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${MainActivity.TOKEN}"
                return headers
            }
        }
        queue.add(stringRequest)
    }
}
