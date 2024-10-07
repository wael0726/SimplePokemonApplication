package com.inf5d6.tp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivityPokemon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        val mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mainActivityViewModel.listePokemons.observe(this) {
            val rvPokemon = findViewById<RecyclerView>(R.id.rvPokemon)
            rvPokemon.adapter = ListePokemonRecyclerViewAdapter(it)
            rvPokemon.layoutManager = GridLayoutManager(this, 2)
        }
    }
}