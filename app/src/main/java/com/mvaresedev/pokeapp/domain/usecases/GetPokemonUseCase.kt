package com.mvaresedev.pokeapp.domain.usecases

import com.mvaresedev.pokeapp.domain.repo.PokeRepository

class GetPokemonUseCase(private val repository: PokeRepository) {
    operator fun invoke() = repository.retrievePokemonFlow()
}