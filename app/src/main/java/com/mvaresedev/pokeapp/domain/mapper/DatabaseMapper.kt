package com.mvaresedev.pokeapp.domain.mapper

import com.mvaresedev.pokeapp.data.db.entities.PokemonEntity
import com.mvaresedev.pokeapp.domain.models.Pokemon

interface DatabaseMapper {
    fun mapPokemonEntityToDomain(entity: PokemonEntity): Pokemon
    fun mapPokemonDomainToEntities(source: List<Pokemon>): List<PokemonEntity>
}