package com.mvaresedev.pokeapp.ui.details

import androidx.lifecycle.viewModelScope
import com.mvaresedev.pokeapp.domain.models.Pokemon
import com.mvaresedev.pokeapp.domain.usecases.GetDetailsUseCase
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsEvent
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsIntent
import com.mvaresedev.pokeapp.ui.details.uimodel.PokemonDetailsState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val getDetailsUseCase: GetDetailsUseCase
) : AndroidDataFlow() {

    val intentChannel = Channel<PokemonDetailsIntent>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when(intent) {
                    is PokemonDetailsIntent.DetailsBackClick, is PokemonDetailsIntent.DetailsButtonBackClick -> {
                        sendNavigateBackEvent()
                    }
                }
            }
        }
    }

    private fun sendNavigateBackEvent() = action {
        sendEvent(PokemonDetailsEvent.NavigateBack)
    }

    fun initFlow(pokemonId: Int) = actionOn<UIState.Empty>  {
        sendLoadingEvent()
        getPokemonDetailFlow(pokemonId)
    }

    private fun getPokemonDetailFlow(pokemonId: Int) {
        viewModelScope.launch {
            getDetailsUseCase(pokemonId)
                    .collectLatest { pokemonDetails ->
                        sendSuccessEvent()
                        if(pokemonDetails != null)
                            setDetailsState(pokemonDetails)
                        else
                            setErrorState()
                    }
        }
    }

    private fun sendLoadingEvent() = action {
        sendEvent(UIEvent.Loading)
    }

    private fun sendSuccessEvent() = action {
        sendEvent(UIEvent.Success)
    }

    private fun setErrorState() = action {
        setState { PokemonDetailsState.DetailsError }
    }

    private fun setDetailsState(pokemonDetails: Pokemon) = action {
        setState { PokemonDetailsState.DetailsPresent(pokemonDetails) }
    }

}