package com.mvaresedev.pokeapp.domain.models

data class Pokemon(
        val id: Int,
        val name: String,
        val iconUrl: String?,
        val bigIconUrl: String?,
        val weight: Int,
        val height: Int,
        val ability: String,
        val types: List<PokemonType>,
        val stats: List<PokemonStat>
)