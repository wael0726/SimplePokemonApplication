package com.inf5d6.tp1.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.inf5d6.tp1.MainActivity
import com.inf5d6.tp1.R
import com.inf5d6.tp1.dataclasses.DetailsPokemon
import com.squareup.picasso.Picasso

class EvolutionAdapter(private val evolutions: List<DetailsPokemon>) :
    RecyclerView.Adapter<EvolutionAdapter.EvolutionViewHolder>() {

    class EvolutionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val evolutionImage: ImageView = itemView.findViewById(R.id.imgEvolution)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evolution, parent, false)
        return EvolutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionViewHolder, position: Int) {
        val evolution = evolutions[position]
        Picasso.get().load(evolution.imgURL).into(holder.evolutionImage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("pokemonId", evolutions[position].pokemonId)
            }

            val navController = (holder.itemView.context as MainActivity).findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_details, bundle)
        }
    }

    override fun getItemCount(): Int = evolutions.size
}