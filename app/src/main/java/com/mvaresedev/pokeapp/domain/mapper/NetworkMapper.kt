package com.mvaresedev.pokeapp.domain.mapper

import com.mvaresedev.pokeapp.data.network.model.PokemonDetailsResponse
import com.mvaresedev.pokeapp.domain.models.Pokemon

interface NetworkMapper {
    fun mapPokemonDetailsResponseToDomain(responseList: List<PokemonDetailsResponse>): List<Pokemon>
}