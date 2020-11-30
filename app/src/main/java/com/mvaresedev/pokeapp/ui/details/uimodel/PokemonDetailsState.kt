package com.mvaresedev.pokeapp.ui.details.uimodel

import com.mvaresedev.pokeapp.domain.models.Pokemon
import io.uniflow.core.flow.data.UIState

sealed class PokemonDetailsState : UIState() {
    data class DetailsPresent(val pokemon: Pokemon) : PokemonDetailsState()
    object DetailsError : PokemonDetailsState()
}