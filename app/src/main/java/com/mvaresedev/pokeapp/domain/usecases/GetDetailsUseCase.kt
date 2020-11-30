package com.mvaresedev.pokeapp.domain.usecases

import com.mvaresedev.pokeapp.domain.repo.PokeRepository

class GetDetailsUseCase(private val repository: PokeRepository) {
    suspend operator fun invoke(pokemonId: Int) = repository.retrieveSinglePokemon(pokemonId)
}