package com.mvaresedev.pokeapp.ui.list.uimodel

import androidx.paging.PagingData
import com.mvaresedev.pokeapp.domain.models.Pokemon
import io.uniflow.core.flow.data.UIState

sealed class PokemonListStates : UIState() {
    data class ListPresent(val pagingData: PagingData<Pokemon>) : PokemonListStates()
    object ListError : PokemonListStates()
}