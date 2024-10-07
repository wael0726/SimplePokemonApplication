package com.inf5d6.tp1.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.inf5d6.tp1.MainActivity
import com.inf5d6.tp1.R
import com.inf5d6.tp1.dataclasses.Pokemon

import com.squareup.picasso.Picasso

class FavoriteRecycleViewAdapter (private val pokemons: Array<Pokemon>) : RecyclerView.Adapter<FavoriteRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_favoris, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.nameFavori).text = pokemons[position].name
        Picasso.get().load(pokemons[position].imgURL).into(holder.view.findViewById<ImageView>(R.id.IMGfavoris))

        holder.view.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("pokemonId", pokemons[position].pokemonId)
            }

            val navController = (holder.view.context as MainActivity).findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_details, bundle)
        }
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }
}