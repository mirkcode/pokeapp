package com.mvaresedev.pokeapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvaresedev.pokeapp.domain.models.PokemonStat
import com.mvaresedev.pokeapp.domain.models.PokemonType

@Entity(tableName = "pokemons")
data class PokemonEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val iconUrl: String?,
        val bigIconUrl: String?,
        val weight: Int,
        val height: Int,
        val ability: String,
        val types: List<PokemonType>,
        val stats: List<PokemonStat>
)