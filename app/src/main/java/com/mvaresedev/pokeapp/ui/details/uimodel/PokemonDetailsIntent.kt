package com.mvaresedev.pokeapp.ui.details.uimodel

sealed class PokemonDetailsIntent {
    object DetailsBackClick: PokemonDetailsIntent()
    object DetailsButtonBackClick: PokemonDetailsIntent()
}