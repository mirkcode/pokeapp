package com.mvaresedev.pokeapp.data.db.mapper

import com.mvaresedev.pokeapp.data.db.entities.PokemonEntity
import com.mvaresedev.pokeapp.domain.mapper.DatabaseMapper
import com.mvaresedev.pokeapp.domain.models.Pokemon

class DatabaseMapperImpl : DatabaseMapper {

    override fun mapPokemonEntityToDomain(entity: PokemonEntity): Pokemon {
        return Pokemon(
            id = entity.id,
            name = entity.name,
            iconUrl = entity.iconUrl,
            bigIconUrl = entity.bigIconUrl,
            weight = entity.weight,
            height = entity.height,
            ability = entity.ability,
            types = entity.types,
            stats = entity.stats
        )
    }

    override fun mapPokemonDomainToEntities(source: List<Pokemon>): List<PokemonEntity> {
        return source.map {
            PokemonEntity(
                id = it.id,
                name = it.name,
                iconUrl = it.iconUrl,
                bigIconUrl = it.bigIconUrl,
                weight = it.weight,
                height = it.height,
                ability = it.ability,
                types = it.types,
                stats = it.stats
            )
        }
    }

}