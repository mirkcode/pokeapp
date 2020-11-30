package com.mvaresedev.pokeapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mvaresedev.pokeapp.data.db.PokemonDatabase
import com.mvaresedev.pokeapp.data.db.entities.PokemonEntity
import com.mvaresedev.pokeapp.data.db.entities.RemoteKeyEntity
import com.mvaresedev.pokeapp.domain.mapper.DatabaseMapper
import com.mvaresedev.pokeapp.data.network.PokemonApi
import com.mvaresedev.pokeapp.domain.mapper.NetworkMapper
import com.mvaresedev.pokeapp.data.network.model.PokemonDetailsResponse
import com.mvaresedev.pokeapp.data.network.model.PokemonListResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PokeNetworkDbRemoteMediator(
        private val pokemonApi: PokemonApi,
        private val dbMapper: DatabaseMapper,
        private val networkMapper: NetworkMapper,
        private val pokemonDb: PokemonDatabase,
        private val initialUrl: String
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokemonEntity>): MediatorResult {
        return try {
            val apiUrl: String = when(loadType){
                LoadType.REFRESH ->
                if(pokemonDb.getPokemonDao().getPokemonsCount() > 0) { //already present some data
                    return MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    initialUrl
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastRemoteKey = getLastRemoteKeys()
                    if(lastRemoteKey?.next != null)
                        lastRemoteKey.next
                    else
                        return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val listResponse = pokemonApi.getPokemonListByUrl(apiUrl)
            val detailsResponse = getPokemonDetailsByList(listResponse.results)
            val pokemonList = networkMapper.mapPokemonDetailsResponseToDomain(detailsResponse)

            pokemonDb.withTransaction {
                pokemonDb.getPokemonDao().insertAllPokemon(dbMapper.mapPokemonDomainToEntities(pokemonList))
                pokemonDb.getRemoteKeyDao().insertKey(RemoteKeyEntity(0, listResponse.next, listResponse.previous))
            }

            MediatorResult.Success(endOfPaginationReached = listResponse.next == null)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getPokemonDetailsByList(results: List<PokemonListResponse.PokemonResult>): List<PokemonDetailsResponse> {

        val deferredDetails: List<Deferred<PokemonDetailsResponse>>

        withContext(Dispatchers.IO) {
            deferredDetails = results.map { item ->
                    val itemDetail = async { pokemonApi.getPokemonDetailsByUrl(item.url) }
                    itemDetail
            }
        }
        return deferredDetails.map { it.await() }
    }

    private suspend fun getLastRemoteKeys(): RemoteKeyEntity? {
        return pokemonDb.getRemoteKeyDao().getAllRemoteKeys().lastOrNull()
    }
}
