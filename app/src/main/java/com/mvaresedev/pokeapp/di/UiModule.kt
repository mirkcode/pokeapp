package com.mvaresedev.pokeapp.di

import com.mvaresedev.pokeapp.ui.details.PokemonDetailsViewModel
import com.mvaresedev.pokeapp.ui.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailsViewModel(get()) }
}