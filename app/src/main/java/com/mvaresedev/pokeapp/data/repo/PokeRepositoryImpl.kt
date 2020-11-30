package com.mvaresedev.pokeapp.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.mvaresedev.pokeapp.data.PokeNetworkDbRemoteMediator
import com.mvaresedev.pokeapp.data.db.PokemonDatabase
import com.mvaresedev.pokeapp.domain.mapper.DatabaseMapper
import com.mvaresedev.pokeapp.data.network.PokemonApi
import com.mvaresedev.pokeapp.domain.mapper.NetworkMapper
import com.mvaresedev.pokeapp.domain.models.Pokemon
import com.mvaresedev.pokeapp.domain.repo.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class PokeRepositoryImpl (
        private val pokemonDb: PokemonDatabase,
        private val pokemonApi: PokemonApi,
        private val dbMapper: DatabaseMapper,
        private val networkMapper: NetworkMapper
): PokeRepository {

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 4
        private const val INIT_URL = "https://pokeapi.co/api/v2/pokemon"
    }

    @ExperimentalPagingApi
    override fun retrievePokemonFlow() = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = PokeNetworkDbRemoteMediator(pokemonApi, dbMapper, networkMapper, pokemonDb, INIT_URL),
            pagingSourceFactory = { pokemonDb.getPokemonDao().getAllPokemons() }
        ).flow.map { pagingData ->
            pagingData.map {  pokemonEntity ->
                dbMapper.mapPokemonEntityToDomain(pokemonEntity)
            }
        }

    override suspend fun retrieveSinglePokemon(pokemonId: Int): Flow<Pokemon?> {
        with(Dispatchers.IO) {
            val pokemonEntity = pokemonDb.getPokemonDao().getPokemonById(pokemonId)
            return flowOf(
                    if(pokemonEntity != null)
                        dbMapper.mapPokemonEntityToDomain(pokemonEntity)
                    else
                        null
            )
        }
    }

}