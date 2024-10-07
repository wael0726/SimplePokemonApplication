package com.inf5d6.tp1.ui.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        view?.findViewById<TextView>(R.id.poketype)?.text = "Poketypes: ${pokemonDetails.poketypes.joinToString(", ") { it.name }}"

        // Récupération des évolutions
        val evolutionsList = mutableListOf<DetailsPokemon>()
        var currentEvolution: DetailsPokemon? = pokemonDetails.evolution

        // Ajout des évolutions à la liste
        while (currentEvolution != null) {
            evolutionsList.add(currentEvolution)
            currentEvolution = currentEvolution.evolution
        }

        // Affichage des images d'évolution dans imgEvo1 et imgEvo2
        val imgEvo1 = view?.findViewById<ImageView>(R.id.ImgEvo1)
        val imgEvo2 = view?.findViewById<ImageView>(R.id.ImgEvo2)

        if (evolutionsList.size > 0) {
            Picasso.get().load(evolutionsList[0].imgURL).into(imgEvo1) // Première évolution
            imgEvo1?.setOnClickListener {
                navigateToDetails(evolutionsList[0].pokemonId) // Navigation vers la première évolution
            }

            if (evolutionsList.size > 1) {
                Picasso.get().load(evolutionsList[1].imgURL).into(imgEvo2) // Deuxième évolution
                imgEvo2?.setOnClickListener {
                    navigateToDetails(evolutionsList[1].pokemonId) // Navigation vers la deuxième évolution
                }
            } else {
                imgEvo2?.visibility = View.GONE // Cacher si pas de deuxième évolution
            }
        } else {
            imgEvo1?.visibility = View.GONE // Cacher si pas d'évolution
            imgEvo2?.visibility = View.GONE // Cacher si pas d'évolution
        }

        checkFavorite(pokemonDetails.pokemonId)
    }

    private fun navigateToDetails(pokemonId: Int) {
        val bundle = Bundle().apply {
            putInt("pokemonId", pokemonId)
        }
        view?.let { navView ->
            val navController = (navView.context as MainActivity).findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_details, bundle)
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
            Request.Method.GET,
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
            btnFavorite?.setBackgroundColor(Color.parseColor("#FF0000")) // Mettre en rouge
        } else {
            btnFavorite?.text = "Ajouter aux favoris"
        }
    }

    private fun addToFavorites(pokemonId: Int) {
        val url = "https://pokemonsapi.herokuapp.com/favorite?pokemonId=$pokemonId"
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = object : StringRequest(
            Request.Method.POST,
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
            Request.Method.DELETE,
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
