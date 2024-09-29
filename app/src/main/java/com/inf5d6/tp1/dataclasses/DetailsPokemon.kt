package com.inf5d6.tp1.dataclasses

data class DetailsPokemon(
    val pokemonId: Int,
    val name: String,
    val color: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialattack: Int,
    val specialdefense: Int,
    val speed: Int,
    val height: Double,
    val weight: Double,
    val thumbURL: String,
    val imgURL: String,
    val cryURL: String,
    val habitat: Habitat,
    val species: Species,
    val poketypes: List<Poketype>,
    val evolution: DetailsPokemon? = null,
)