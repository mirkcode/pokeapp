package com.mvaresedev.pokeapp.ui.list.uimodel

import androidx.paging.CombinedLoadStates
import com.mvaresedev.pokeapp.domain.models.Pokemon

sealed class PokemonListIntent {
    class ListStateChange(val itemsLoaded: Int, val state: CombinedLoadStates) : PokemonListIntent()
    class PokemonItemClick(val pokemon: Pokemon) : PokemonListIntent()
}