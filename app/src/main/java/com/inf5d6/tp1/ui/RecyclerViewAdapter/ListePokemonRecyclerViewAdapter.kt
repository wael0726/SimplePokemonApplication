package com.inf5d6.tp1

import android.view.LayoutInflater
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ListePokemonRecyclerViewAdapter(val listePokemon: Array<Pokemon>) :
    RecyclerView.Adapter<ListePokemonRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.namepokemon).text = listePokemon[position].name
        Picasso.get().load(listePokemon[position].imgURL).into(holder.view.findViewById<ImageView>(R.id.imgURL))

        holder.view.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("pokemonId", listePokemon[position].pokemonId)
            }

            holder.view?.let { view ->
                val navController = (view.context as MainActivity).findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.navigation_details, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return listePokemon.size
    }
}
