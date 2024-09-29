package com.inf5d6.tp1.dataclasses

data class Pokemon(
    val pokemonId: Int,
    val name: String,
    val color: String,
    val thumbURL: String,
    val imgURL: String,
    val cryURL: String,
    val habitat: Habitat,
    val species: Species,
    val poketypes: List<Poketype>,
)

