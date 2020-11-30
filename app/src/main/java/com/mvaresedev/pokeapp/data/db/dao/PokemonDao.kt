package com.mvaresedev.pokeapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.pokeapp.data.db.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons")
    fun getAllPokemons(): PagingSource<Int, PokemonEntity>

    @Query("SELECT COUNT(id) FROM pokemons")
    suspend fun getPokemonsCount(): Int

    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity?

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemon()
}