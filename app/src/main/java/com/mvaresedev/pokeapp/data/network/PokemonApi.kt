package com.mvaresedev.pokeapp.data.network

import com.mvaresedev.pokeapp.data.network.model.PokemonDetailsResponse
import com.mvaresedev.pokeapp.data.network.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {

    @GET
    suspend fun getPokemonListByUrl(@Url url: String): PokemonListResponse

    @GET
    suspend fun getPokemonDetailsByUrl(@Url url: String): PokemonDetailsResponse

}