package com.mvaresedev.pokeapp.ui.details.uimodel

import io.uniflow.core.flow.data.UIEvent

sealed class PokemonDetailsEvent: UIEvent() {
    object NavigateBack : PokemonDetailsEvent()
}