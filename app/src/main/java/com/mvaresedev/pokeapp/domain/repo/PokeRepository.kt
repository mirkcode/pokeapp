package com.mvaresedev.pokeapp.domain.repo

import androidx.paging.PagingData
import com.mvaresedev.pokeapp.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun retrievePokemonFlow(): Flow<PagingData<Pokemon>>
    suspend fun retrieveSinglePokemon(pokemonId: Int): Flow<Pokemon?>
}